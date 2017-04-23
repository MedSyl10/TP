// Repris de http://www.lamsade.dauphine.fr/rigaux/mysql/
// et adapt� pour cr�er et manipuler un objet Departement 
// � partir d'un nuplet de la relation Departement

import java.sql.*;


public class Departement {
    private int id;
    private String nom;
    private boolean _builtFromDB;
    private static String _query = "SELECT * FROM Departement";
    
    private String _update() {
            return "UPDATE Departement SET Nom_Departement='" + nom +
             "' WHERE Departement_ID=" + id;
    }
   
    private String _insert() {
            return "INSERT INTO Departement"
            + " VALUES(nextval('departement_departement_id_seq'), '" + 
            nom + "')";
    }
    
    private String _delete() {
        return "DELETE FROM Departement"
        + " WHERE Departement_ID = " + id ;
    }

    public Departement() {
    	_builtFromDB=false;
    }
    
    public Departement(String nom) {
    	this.nom=nom; id=-1; _builtFromDB=false;
    }
    
    public String toString() {
        return "D�partement " + id + " : " + nom ;
      }
    
    public Departement(ResultSet rs) throws SQLException {
        id = rs.getInt("Departement_ID");
        nom = rs.getString("Nom_Departement");
        _builtFromDB = true;
    }
    
    public int getId() {return id;}
    
    public void setId(int id) {this.id=id;} 
    
    public String getNom() {return nom;}
    
    public void setNom(String nom) {this.nom=nom;} 
    
    public boolean equals(Object other) { 
    	if (this == other) return true; 
    	if ( !(other instanceof Departement) ) return false;  
    	final Departement obj = (Departement) other; 
    	   if ( obj.getNom()!=getNom() ) 
    	     return false;           
    	return true; 
    }
    
    public int hashCode(){
    	return nom.hashCode() ;
    	}
    
    public void save(Connection cx) throws SQLException {
        Statement s = cx.createStatement();
        if(_builtFromDB) {
        	System.out.println("Executing this command: "+_update()+"\n");
            s.executeUpdate(_update());
        }
        else {
        	// R�cuperation de la cl� g�n�r�e par la s�quence
        	
        	// Code ne fonctionnant pas avec le driver JDBC de PostgreSQL
        	// Mais apparemment avec celui de MySQL OUI.
            /*s.executeUpdate(_insert(), Statement.RETURN_GENERATED_KEYS);
            ResultSet r = s.getGeneratedKeys();
            while(r.next())
                id = r.getInt(1);
                */
        	
        	
        	System.out.println("Executing this command: "+_insert()+"\n");
        	s.executeUpdate(_insert());
        	_builtFromDB=true;
        	
        	// Pour r�cuperer la cl� g�n�r�e sous PostgreSQL
        	ResultSet rset =s.executeQuery("SELECT last_value FROM departement_departement_id_seq");
        	if (rset.next()) {
    			//Column numbered from 1 (not from zero)
    			id = rset.getInt(1);
        	}
        }
    }
    
    public void delete(Connection cx) throws SQLException {
    	Statement s = cx.createStatement();
        if(_builtFromDB) {
        	System.out.println("Executing this command: "+_delete()+"\n");
            s.executeUpdate(_delete());
        }
        else System.out.println("Objet non persistant!");
    }

    public String getQuery() {
        return _query;
    }
}
