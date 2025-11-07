package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.model;

import java.time.Instant;
import java.util.Objects;

/**
 * Documento NF-e simplificado. Pode ser modificado por validadores (ex.: normalização)
 * e precisa suportar rollback (feito pelos próprios validadores reversíveis).
 *
 * SRP: apenas dados do documento. Imutabilidade parcial não é requerida aqui
 * pois alguns validadores simulam "escritas" (ex.: DB reserva/lock).
 */
public class NFe {
    private String numero;
    private String emitenteCnpj;
    private String destinatarioCnpj;
    private double valorTotal;
    private Instant emissao;
    private String xml;              // payload bruto para o validador de schema
    private String certificadoSerial; // id do certificado

    public NFe(String numero, String emitenteCnpj, String destinatarioCnpj, double valorTotal,
               Instant emissao, String xml, String certificadoSerial) {
        this.numero = Objects.requireNonNull(numero);
        this.emitenteCnpj = Objects.requireNonNull(emitenteCnpj);
        this.destinatarioCnpj = Objects.requireNonNull(destinatarioCnpj);
        this.valorTotal = valorTotal;
        this.emissao = Objects.requireNonNull(emissao);
        this.xml = Objects.requireNonNull(xml);
        this.certificadoSerial = Objects.requireNonNull(certificadoSerial);
    }

    public String getNumero() { return numero; }
    public String getEmitenteCnpj() { return emitenteCnpj; }
    public String getDestinatarioCnpj() { return destinatarioCnpj; }
    public double getValorTotal() { return valorTotal; }
    public Instant getEmissao() { return emissao; }
    public String getXml() { return xml; }
    public String getCertificadoSerial() { return certificadoSerial; }

    // Exemplo de modificação possível por validadores (normalização etc.)
    public void setXml(String xml) { this.xml = xml; }

    @Override public String toString() {
        return "NFe{" +
                "numero='" + numero + '\'' +
                ", valorTotal=" + valorTotal +
                ", emissao=" + emissao +
                '}';
    }
}
