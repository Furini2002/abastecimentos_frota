package br.com.abastecimentofrota;

import br.com.abastecimentofrota.service.AbastecimentoService;
import br.com.abastecimentofrota.service.AtualizarAbastecimentoService;
import br.com.abastecimentofrota.service.NotaFiscalCupomService;
import br.com.abastecimentofrota.service.NotaFiscalService;
import br.com.abastecimentofrota.service.PostoService;
import br.com.abastecimentofrota.service.RelatorioAbastecimentoService;
import br.com.abastecimentofrota.service.VeiculoService;
import br.com.abastecimentofrota.ui.TelaPrincipal;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AbastecimentoFrotaApplication {

    public static void main(String[] args) {
        // Define o tema antes de iniciar a interface gráfica
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Erro ao aplicar o tema FlatLaf: " + ex.getMessage());
        }

        // Inicia o Spring Boot normalmente
        ConfigurableApplicationContext context = SpringApplication.run(AbastecimentoFrotaApplication.class, args);

        // Injeta os serviços necessários na UI
        AbastecimentoService abastecimentoService = context.getBean(AbastecimentoService.class);
        VeiculoService veiculoService = context.getBean(VeiculoService.class);
        PostoService postoService = context.getBean(PostoService.class);
        NotaFiscalService notaService = context.getBean(NotaFiscalService.class);
        NotaFiscalCupomService notaCupomService = context.getBean(NotaFiscalCupomService.class);
        RelatorioAbastecimentoService relatorioService = context.getBean(RelatorioAbastecimentoService.class);
        AtualizarAbastecimentoService atualizarAbastecimento = context.getBean(AtualizarAbastecimentoService.class);
        
        // Inicializa a interface com o tema aplicado
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal(atualizarAbastecimento, veiculoService, abastecimentoService, postoService, notaService, notaCupomService, relatorioService);
            tela.setVisible(true);
        });
    }
}
