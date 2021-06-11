/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package model;

public interface Observer extends java.io.Serializable {

    void update(Evento evento);

}
