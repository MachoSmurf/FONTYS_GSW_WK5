package aex.server;


import aex.client.IFonds;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IEffectenBeurs extends Remote{
    List<IFonds> getKoersen() throws RemoteException;
}
