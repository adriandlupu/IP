import java.util.Vector;

public interface Planta {

    /**
   * 
   * @element-type Client
   */
  public Vector  myClient;
    public Asistent myAsistent;

  public String descriere();

  public String denumire();

  public String codQr();

}