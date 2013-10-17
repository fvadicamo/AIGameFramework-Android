package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class Pawn extends Piece
{
    public Pawn(int id)
    {
        super(id);
    }

    /**
     * Returns a {@code Pawn} instance with the specified id.
     * <p>
     * If it is not necessary to get a new {@code Pawn} instance, it is
     * recommended to use this method instead of the constructor, since it
     * maintains a cache of instances which may result in better performance.
     *
     * @param id
     *            the piece identifier.
     * @return a {@code Pawn} instance with the specified {@code id}.
     */
    public static Pawn valueOf(int id) {
        return  id >= 128 || id < -128 ? new Pawn(id) : SMALL_VALUES[id + 128];
    }

    /**
     * A cache of instances used by {@link Pawn#valueOf(int)}
     */
    private static final Pawn[] SMALL_VALUES = new Pawn[256];

    static {
        for (int i = -128; i < 128; i++) {
            SMALL_VALUES[i + 128] = new Pawn(i);
        }
    }
}
