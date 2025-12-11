package br.com.pedidos.application.usecase.sistema_pedidos;

import br.com.pedidos.sistema_pedidos.application.usecase.CriarCategoriaUseCase;
import br.com.pedidos.sistema_pedidos.domain.model.Categoria;
import br.com.pedidos.sistema_pedidos.domain.repository.CategoriaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CriarCategoriaUseCaseTest {

    @Mock
    private CategoriaRepositoryPort repository;

    @InjectMocks
    private CriarCategoriaUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarCategoriaComSucesso() {
        // Arrange
        Categoria categoria = new Categoria(null, "Lanches");

        when(repository.salvar(any(Categoria.class)))
                .thenReturn(new Categoria(1L, "Lanches"));

        // Act
        Categoria resultado = useCase.executar(categoria);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Lanches", resultado.getNome());

        verify(repository, times(1)).salvar(any());
    }
}
