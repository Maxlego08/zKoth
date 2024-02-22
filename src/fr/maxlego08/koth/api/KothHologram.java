package fr.maxlego08.koth.api;

public interface KothHologram {

    void start(Koth koth);

    void end(Koth koth);

    void update(Koth koth);

    void onDisable();

}
