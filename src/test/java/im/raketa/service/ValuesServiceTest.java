package im.raketa.service;

import im.raketa.api.ValueDTO;
import im.raketa.db.Value;
import im.raketa.db.ValuesRepo;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class ValuesServiceTest {

    ValuesRepo valuesRepo = mock(ValuesRepo.class);
    ApiService apiService = new ApiService(valuesRepo);

    @Test
    void valuesCreation() {
        List<Value> testValues = apiService.valuesCreation(3);
        verify(valuesRepo).saveAll(testValues);
        assertEquals(3, testValues.size());
    }

    @Test
    void eraseDataBase() {
        Value value = getValue();
        when(valuesRepo.existsById(1L)).thenReturn(true);
        apiService.eraseDataBase();
        assertNotNull(value);
        verify(valuesRepo).deleteAll();
    }

    @Test
    void updateValue() {
        Value value = getValue();
        ValueDTO valueDTO = new ValueDTO("Новая Запись");
        when(valuesRepo.existsById(1L)).thenReturn(true);
        when(valuesRepo.findValueById(1L)).thenReturn(value);
        Value testValue = apiService.updateValue(1L, valueDTO);
        assertNotNull(testValue);
        assertEquals(1L, testValue.getId());
        assertEquals("Новая Запись", testValue.getValue());
    }

    private Value getValue() {
        return new Value(1L, Instant.parse("2022-01-20T18:00:00.00Z"), "Запись 1");
    }
}