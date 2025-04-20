package src;
public class Recensione {
    private String Commento, Username, NomeRistorante;
    private int idUtente;
    private double Voto;

    public Recensione(int Voto, String Commento, String Username, String NomeRistorante){
        this.Commento = Commento;
        this.Voto = Voto;
        this.NomeRistorante = NomeRistorante;
        this.Username = Username;
    }

    public String getNomeRistorante(){return NomeRistorante;}
    
    public void setCommento(String newCommento){Commento = newCommento;}
    public void setVoto(int newVoto){Voto = newVoto;}

    @Override
    public String toString(){
        return "Recensione di: " + Username + "\nVoto: " + Voto + "\nCommento: " + Commento;
    }
}
