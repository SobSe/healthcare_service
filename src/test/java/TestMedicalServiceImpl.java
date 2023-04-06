import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

public class TestMedicalServiceImpl {
    @ParameterizedTest
    @MethodSource("sourceCheckBloodPressure")
    public void testCheckBloodPressure(String patientId, PatientInfo patientInfo,
                                       BloodPressure bloodPressure, int wantedNumberOfInvocations, String message) {
        //arrange
        PatientInfoRepository repository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(repository.getById(patientId)).thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(repository, sendAlertService);
        //act
        medicalService.checkBloodPressure(patientId, bloodPressure);
        //assert
        Mockito.verify(sendAlertService, Mockito.times(wantedNumberOfInvocations)).send(message);
    }

    public static Stream<Arguments> sourceCheckBloodPressure() {
        return Stream.of(
                Arguments.of(
                        "1"
                        , new PatientInfo("1", "Bard"
                                , "Petrov", LocalDate.of(1980, 1, 15)
                                , new HealthInfo(BigDecimal.valueOf(36.6), new BloodPressure(120, 80)))
                        , new BloodPressure(130, 80)
                        , 1
                        , "Warning, patient with id: 1, need help"
                ),
                Arguments.of(
                        "1"
                        , new PatientInfo("1", "Bard"
                                , "Petrov", LocalDate.of(1980, 1, 15)
                                , new HealthInfo(BigDecimal.valueOf(36.6), new BloodPressure(120, 80)))
                        , new BloodPressure(120, 80)
                        , 0
                        , "Warning, patient with id: 1, need help"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("sourceMessageCheckBloodPressure")
    public void testMessageCheckBloodPressure(String patientId, PatientInfo patientInfo,
                                       BloodPressure bloodPressure, int wantedNumberOfInvocations, String message) {
        //arrange
        PatientInfoRepository repository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(repository.getById(patientId)).thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(repository, sendAlertService);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        //act
        medicalService.checkBloodPressure(patientId, bloodPressure);
        //assert
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        Assertions.assertEquals(message, argumentCaptor.getValue());
    }

    public static Stream<Arguments> sourceMessageCheckBloodPressure() {
        return Stream.of(
                Arguments.of(
                        "1"
                        , new PatientInfo("1", "Bard"
                                , "Petrov", LocalDate.of(1980, 1, 15)
                                , new HealthInfo(BigDecimal.valueOf(36.6), new BloodPressure(120, 80)))
                        , new BloodPressure(130, 80)
                        , 1
                        , "Warning, patient with id: 1, need help"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("sourceCheckTemperature")
    public void testCheckTemperature(String patientId, PatientInfo patientInfo,
                                       BigDecimal temperature, int wantedNumberOfInvocations, String message) {
        //arrange
        PatientInfoRepository repository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(repository.getById(patientId)).thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(repository, sendAlertService);
        //act
        medicalService.checkTemperature(patientId, temperature);
        //assert
        Mockito.verify(sendAlertService, Mockito.times(wantedNumberOfInvocations)).send(message);
    }

    public static Stream<Arguments> sourceCheckTemperature() {
        return Stream.of(
                Arguments.of(
                        "1"
                        , new PatientInfo("1", "Bard"
                                , "Petrov", LocalDate.of(1980, 1, 15)
                                , new HealthInfo(BigDecimal.valueOf(36.6), new BloodPressure(120, 80)))
                        , BigDecimal.valueOf(34.5)
                        , 1
                        , "Warning, patient with id: 1, need help"
                ),
                Arguments.of(
                        "1"
                        , new PatientInfo("1", "Bard"
                                , "Petrov", LocalDate.of(1980, 1, 15)
                                , new HealthInfo(BigDecimal.valueOf(36.6), new BloodPressure(120, 80)))
                        , BigDecimal.valueOf(36.6)
                        , 0
                        , "Warning, patient with id: 1, need help"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("sourceMessageCheckTemperature")
    public void testMessageCheckTemperature(String patientId, PatientInfo patientInfo,
                                     BigDecimal temperature, int wantedNumberOfInvocations, String message) {
        //arrange
        PatientInfoRepository repository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(repository.getById(patientId)).thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(repository, sendAlertService);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        //act
        medicalService.checkTemperature(patientId, temperature);
        //assert
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        Assertions.assertEquals(message, argumentCaptor.getValue());
    }

    public static Stream<Arguments> sourceMessageCheckTemperature() {
        return Stream.of(
                Arguments.of(
                        "1"
                        , new PatientInfo("1", "Bard"
                                , "Petrov", LocalDate.of(1980, 1, 15)
                                , new HealthInfo(BigDecimal.valueOf(36.6), new BloodPressure(120, 80)))
                        , BigDecimal.valueOf(34.5)
                        , 1
                        , "Warning, patient with id: 1, need help"
                )
        );
    }

    @Test
    public void testPatientNotFound() {
        //arrange
        PatientInfoRepository repository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(repository.getById(Mockito.anyString())).thenReturn(null);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(repository, sendAlertService);
        //act
        Executable executable = () -> medicalService.checkTemperature("2", new BigDecimal(36.6));
        //assert
        Assertions.assertThrowsExactly(RuntimeException.class, executable);
    }
}
