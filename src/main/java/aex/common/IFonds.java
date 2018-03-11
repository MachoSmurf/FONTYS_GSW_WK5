package aex.common;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFonds extends Serializable{

    String getNaam();
    double getKoers();
}
