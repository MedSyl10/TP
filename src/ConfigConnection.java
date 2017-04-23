/* Repris de http://deptinfo.unice.fr/~grin/mescours/minfo/bdavancees/tp/tpjdbc1/ConfigConnection.java
* Pour am�liorer la portabilit� de vos programmes, il est conseill� 
*  de ne pas �crire "en dur" dans le programme les informations 
*  n�cessaires � la connexion. On peut ainsi modifier ces informations 
* sans recompiler les classes qui les utilisent.
*/

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ConfigConnection.java
 * Initialise une connexion � une base
 * en lisant un fichier de propri�t�s
 * @version 2 (avec getResource)
 */

public class ConfigConnection {

  private ConfigConnection() { }

  /**
   * Obtenir une connexion � partir des infos qui sont
   * dans un fichier de propri�t�s.
   * Le fichier doit contenir les propri�t�s driver, url,
   * utilisateur, mdp (mot de passe).
   * @param nomFichierProp nom du fichier de propri�t�s. Si le nom
   * commence par "/", l'emplacement d�signe un endroit relatif
   * au classpath, sinon il d�signe un endroit relatif au
   * r�pertoire qui contient le fichier ConfigConnection.class.
   * @return une connexion � la base
   */
  public static Connection getConnection(String nomFichierProp)
      throws IOException, ClassNotFoundException, SQLException {
    Properties props = new Properties();
    URL urlFichierProp = ConfigConnection.class.getResource(nomFichierProp);
    BufferedInputStream bis = null;
    try {
      bis = new BufferedInputStream(urlFichierProp.openStream());
      props.load(bis);
      String driver = props.getProperty("driver");
      String url = props.getProperty("url");
      String utilisateur = props.getProperty("utilisateur");
      String mdp = props.getProperty("mdp");
      Class.forName(driver);
      return DriverManager.getConnection(url, utilisateur, mdp);
    }
    finally {
      if (bis != null) {
        bis.close();
      }
    }
  }

  /**
   * Obtenir une connexion � partir des infos qui sont
   * dans un fichier de propri�t�s, du nom d'utilisateur
   * et du mot de passe pass�s en param�tre.
   * Le fichier doit contenir les propri�t�s driver, url.
   * Le nom et le mot de passe de l'utilisateur sont pass�s
   * en param�tre de la m�thode.
   * @param nomFichierProp nom du fichier de propri�t�s. Si le nom
   * commence par "/", l'emplacement d�signe un endroit relatif
   * au classpath, sinon il d�signe un endroit relatif au
   * r�pertoire qui contient le fichier ConfigConnection.class.
   * @param utilisateur nom de l'utilisateur.
   * @param mdp mot de passe de l'utilisateur.
   * @return une connexion � la base.
   */
  public static Connection getConnection()
      throws IOException, ClassNotFoundException, SQLException {
    Properties props = new Properties();
    String nomFichierProp = "C:\\Users\\THINKPAD\\workspace\\TP\\config.properties";
	//URL urlFichierProp = ConfigConnection.class.getResource(nomFichierProp );
    //BufferedInputStream bis = null;
    InputStream bis = null;
    try {
      bis = new FileInputStream(nomFichierProp);
      props.load(bis);
      String driver = props.getProperty("driver");
      String url = props.getProperty("url");
      String utilisateur = props.getProperty("utilisateur");
      String mdp = props.getProperty("mdp");
     
      Class.forName(driver);
      
      return DriverManager.getConnection(url, utilisateur, mdp);
    }
    finally {
      if (bis != null) {
        bis.close();
      }
    }
  }
} // ConfigConnexion