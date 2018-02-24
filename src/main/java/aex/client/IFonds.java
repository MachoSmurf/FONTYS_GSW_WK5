package aex.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFonds extends Remote{

    String getNaam() throws RemoteException;
    double getKoers() throws RemoteException;
    void updateKoers() throws RemoteException;
}
