package exercicio_4;

import java.util.List;

/**
 * Decorator concreto: “exporta” para PDF.
 * Aqui, simulamos gerando um arquivo .pdf fictício com o conteúdo textual.
 * Em produção, você trocaria a implementação por uma lib de PDF.
 */
public class RelatorioExportPDF extends RelatorioDecorator {
    private final String caminhoArquivo;

    public RelatorioExportPDF(Relatorio wrappee, String caminhoArquivo) {
        super(wrappee);
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public String gerar(List<Pedido> pedidos) {
        String conteudo = super.gerar(pedidos);

        // Simulação de exportação (sem libs): grava .txt com extensão .pdf
        try (java.io.PrintWriter out = new java.io.PrintWriter(caminhoArquivo)) {
            out.println(conteudo);
        } catch (Exception e) {
            return conteudo + "\n[EXPORTAÇÃO PDF FALHOU: " + e.getMessage() + "]\n";
        }

        return conteudo + "\n[PDF exportado em: " + caminhoArquivo + "]\n";
    }
}
