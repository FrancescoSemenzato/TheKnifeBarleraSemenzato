package src;
public class Recensione {
    private String Commento, Username, NomeRistorante, Risposta;
    private int Voto;

    public Recensione(int Voto, String Commento, String Username, String NomeRistorante){
        this.Commento = Commento;
        this.Voto = Voto;
        this.NomeRistorante = NomeRistorante;
        this.Username = Username;
    }

    public String getNomeRistorante(){return NomeRistorante;}
    public String getCommento(){return Commento;}
    public int getVoto(){return Voto;}
    public String getRisposta(){return Risposta;}
    public String getUsername(){return Username;}
    
    public void setCommento(String newCommento){Commento = newCommento;}
    public void setVoto(int newVoto){Voto = newVoto;}
    public void setRisposta(String newRisposta){Risposta = newRisposta;}

    @Override
    public String toString(){
        return "Recensione di: " + Username + "\nVoto: " + Voto + "\nCommento: " + Commento;
    }
}
