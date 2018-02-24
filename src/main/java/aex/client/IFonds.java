package aex.client;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFonds extends Remote, Serializable{

    String getNaam() throws RemoteException;
    double getKoers() throws RemoteException;
    void updateKoers() throws RemoteException;
}
