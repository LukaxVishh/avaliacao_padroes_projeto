package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.infra;

/** SEFAZ fake: autoriza números que terminam com dígito par (dummy). */
public final class FakeSefazService {
    public boolean authorize(String numero, String emitenteCnpj, double valor) throws InterruptedException {
        Thread.sleep(150); // simula latência
        char last = numero.charAt(numero.length()-1);
        return Character.isDigit(last) && ((last - '0') % 2 == 0);
    }
}
