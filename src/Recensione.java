package src;
public class Recensione {
    private String Commento, Username, NomeRistorante, Risposta = "";
    private int Voto;

    public Recensione(int Voto, String Commento, String Username, String NomeRistorante){
        this.Commento = Commento;
        this.Voto = Voto;
        this.NomeRistorante = NomeRistorante;
        this.Username = Username;
    }
    public Recensione(int Voto, String Commento, String Username, String NomeRistorante, String Risposta){
        this.Commento = Commento;
        this.Voto = Voto;
        this.NomeRistorante = NomeRistorante;
        this.Username = Username;
        this.Risposta = Risposta;
    }

    public String getNomeRistorante(){return NomeRistorante;}
    public String getCommento(){return Commento;}
    public int getVoto(){return Voto;}
    public String getRisposta(){return Risposta;}
    public String getUsername(){return Username;}
    
    public void setCommento(String newCommento){this.Commento = newCommento;}
    public void setVoto(int newVoto){this.Voto = newVoto;}
    public void setRisposta(String newRisposta){this.Risposta = newRisposta;}

    public void EliminaRisposta(){this.Risposta = "";}

    public String visualizzaRecensione(){
        if(Risposta.equals(""))
            return "Recensione di: " + Username + "\nVoto: " + Voto + "\nCommento: " + Commento;
        return "Recensione di: " + Username + "\nVoto: " + Voto + "\nCommento: " + Commento + "\nRisposta: " + Risposta;
    }

    @Override
    public String toString(){
        return "Recensione di: " + Username + "\nVoto: " + Voto + "\nCommento: " + Commento + "\nRisposta: " + Risposta;
    }
    @Override
    public boolean equals(Object object){
        if(object instanceof Recensione){
            Recensione rec = (Recensione) object;
            return rec.getVoto() == Voto && rec.getCommento().equals(Commento) && rec.getRisposta().equals(Risposta);
        }
        return false;
    }
}
