package src.models;

/**
 * Nome: Francesco
 * Cognome: Semenzato
 * Matricola: 760120
 * Sede: Varese
 *
 * Nome: Marco
 * Cognome: Barlera
 * Matricola: 760000
 * Sede: Varese
 * 
 * Descrizione: Rappresenta una recensione per un ristorante, con voto, commento, username dell'autore,
 * nome del ristorante e una possibile risposta da parte del ristoratore.
 * 
 * @author Semenzato Francesco 
 * @author Barlera Marco
 */
public class Recensione {
    private String Commento, Username, NomeRistorante, Risposta = "";
    private int Voto;

    /**
     * Crea una recensione senza risposta.
     *
     * @param Voto Il voto da 1 a 5.
     * @param Commento Il commento dell'utente.
     * @param Username L'autore della recensione.
     * @param NomeRistorante Il ristorante recensito.
     */
    public Recensione(int Voto, String Commento, String Username, String NomeRistorante){
        this.Commento = Commento;
        this.Voto = Voto;
        this.NomeRistorante = NomeRistorante;
        this.Username = Username;
    }

    /**
     * Crea una recensione con risposta del ristoratore.
     *
     * @param Voto Il voto da 1 a 5.
     * @param Commento Il commento dell'utente.
     * @param Username L'autore della recensione.
     * @param NomeRistorante Il ristorante recensito.
     * @param Risposta La risposta del ristoratore.
     */
    public Recensione(int Voto, String Commento, String Username, String NomeRistorante, String Risposta){
        this.Commento = Commento;
        this.Voto = Voto;
        this.NomeRistorante = NomeRistorante;
        this.Username = Username;
        this.Risposta = Risposta;
    }

    /**
     * Restituisce il nome del ristorante.
     *
     * @return Nome del ristorante recensito.
     */
    public String getNomeRistorante(){return NomeRistorante;}

    /**
     * Restituisce il commento della recensione.
     *
     * @return Commento della recensione.
     */
    public String getCommento(){return Commento;}

    /**
     * Restituisce il voto della recensione.
     *
     * @return Voto della recensione.
     */
    public int getVoto(){return Voto;}

    /**
     * Restituisce la risposta del ristoratore.
     *
     * @return Risposta associata alla recensione.
     */
    public String getRisposta(){return Risposta;}

    /**
     * Restituisce l'username dell'autore della recensione.
     *
     * @return Username dell'autore.
     */
    public String getUsername(){return Username;}
    
    /**
     * Modifica il commento della recensione.
     * @param newCommento Nuovo commento da associare
     */
    public void setCommento(String newCommento){this.Commento = newCommento;}

    /**
     * Modifica il voto della recensione.
     * @param newVoto Nuovo voto da associare
     */
    public void setVoto(int newVoto){this.Voto = newVoto;}
    
    /**
     * Modifica la risposta della recensione.
     * @param newRisposta Nuova risposta da associare
     */
    public void setRisposta(String newRisposta){this.Risposta = newRisposta;}

    /**
     * Rimuove la risposta associata alla recensione.
     */
    public void EliminaRisposta(){this.Risposta = "";}

    /**
     * Restituisce una rappresentazione leggibile della recensione, comprensiva della risposta se presente.
     *
     * @return Stringa formattata con i dettagli della recensione.
     */
    public String visualizzaRecensione(){
        if(Risposta.equals(""))
            return "Recensione di: " + Username + "\nVoto: " + Voto + "\nCommento: " + Commento;
        return "Recensione di: " + Username + "\nVoto: " + Voto + "\nCommento: " + Commento + "\nRisposta: " + Risposta;
    }

    /**
     * Restituisce una rappresentazione testuale della recensione, sempre comprensiva della risposta.
     *
     * @return Stringa con tutti i dettagli della recensione.
     */
    @Override
    public String toString(){
        return "Recensione di: " + Username + "\nVoto: " + Voto + "\nCommento: " + Commento + "\nRisposta: " + Risposta;
    }

    /**
     * Confronta due recensioni sulla base di voto, commento e risposta.
     *
     * @param object L'oggetto da confrontare.
     * @return true se le recensioni sono considerate uguali, false altrimenti.
     */
    @Override
    public boolean equals(Object object){
        if(object instanceof Recensione){
            Recensione rec = (Recensione) object;
            return rec.getVoto() == Voto && rec.getCommento().equals(Commento) && rec.getRisposta().equals(Risposta);
        }
        return false;
    }
}
