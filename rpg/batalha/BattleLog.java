package rpg.batalha;

import java.util.ArrayList;
import java.util.List;

public class BattleLog {
    private final List<String> linhas = new ArrayList<>();
    public void add(String s) { linhas.add(s); }
    public List<String> linhas() { return linhas; }
    public void dumpToStdout() { linhas.forEach(System.out::println); }
}
