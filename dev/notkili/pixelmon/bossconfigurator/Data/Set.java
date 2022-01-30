package dev.notkili.pixelmon.bossconfigurator.Data;

import com.google.gson.annotations.Expose;

import javax.annotation.Nullable;

public class Set {
    @Expose
    @Nullable
    private String move1 = null;

    @Expose
    @Nullable
    private String move2 = null;

    @Expose
    @Nullable
    private String move3 = null;

    @Expose
    @Nullable
    private String move4 = null;

    public Set(String ... args) {
        if (args.length >= 1) {
            move1 = args[0];
        }

        if (args.length >= 2) {
            move2 = args[1];
        }

        if (args.length >= 3) {
            move3 = args[2];
        }

        if (args.length >= 4) {
            move4 = args[3];
        }
    }

    @Nullable
    public String getMove1() {
        return move1;
    }

    @Nullable
    public String getMove2() {
        return move2;
    }

    @Nullable
    public String getMove3() {
        return move3;
    }

    @Nullable
    public String getMove4() {
        return move4;
    }
}
