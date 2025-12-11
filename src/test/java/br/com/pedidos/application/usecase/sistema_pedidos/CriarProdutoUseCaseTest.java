package br.com.pedidos.application.usecase.sistema_pedidos;


import br.com.pedidos.sistema_pedidos.application.usecase.CriarProdutoUseCase;
import br.com.pedidos.sistema_pedidos.domain.model.*;
import br.com.pedidos.sistema_pedidos.domain.repository.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CriarProdutoUseCaseTest {

    @Mock
    ProdutoRepositoryPort produtoRepo;

    @Mock
    CategoriaRepositoryPort categoriaRepo;

    @InjectMocks
    CriarProdutoUseCase useCase;

    @BeforeEach
    void setup() { MockitoAnnotations.openMocks(this); }

    @Test
    void deveCriarProdutoQuandoCategoriaPermitida() {
        Produto novo = new Produto("Coca", BigDecimal.TEN, new Categoria(1L));

        Categoria cat = new Categoria(1L, "Bebida");
        when(categoriaRepo.buscarPorId(1L)).thenReturn(Optional.of(cat));

        when(produtoRepo.salvar(any())).thenReturn(novo);

        Produto criado = useCase.executar(novo, 1L);

        assertEquals("Coca", criado.getNome());
        verify(produtoRepo).salvar(any());
    }

    @Test
    void deveFalharQuandoCategoriaNaoEncontrada() {
        Produto novo = new Produto("Coca", BigDecimal.TEN, new Categoria("Bebida"));
        when(categoriaRepo.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> useCase.executar(novo, 1L));
    }

    @Test
    void deveFalharQuandoCategoriaNaoPermitida() {
        Produto novo = new Produto("Coca", BigDecimal.TEN, new Categoria(99L));
        Categoria cat = new Categoria(99L, "Aleatorio");

        when(categoriaRepo.buscarPorId(99L)).thenReturn(Optional.of(cat));

        assertThrows(RuntimeException.class,
                () -> useCase.executar(novo, 99L));
    }
}

