package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.CoppiaDirettori;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;


public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> getDirectors (int anno, Map<Integer, Director> idMap) {
	String sql ="SELECT d.id, d.first_name, d.last_name "
			+ "FROM directors d, movies_directors md, movies m "
			+ "WHERE d.id=md.director_id AND md.movie_id=m.id AND m.year=?";
	List<Director> result = new ArrayList<Director>();
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, anno);
		ResultSet res = st.executeQuery();
		while (res.next()) {

			Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
			idMap.put(director.getId(), director);
			result.add(director);
		}
		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
}
	
//	public List<CoppiaDirettori> getCoppiaDirettori (Map<Integer, Director>idMap) {
//		String sql="SELECT DISTINCT d1.id, d2.id "
//				+ "FROM directors d1, directors d2";
//		Connection conn= DBConnect.getConnection();
//		List<CoppiaDirettori> result= new ArrayList<CoppiaDirettori>();
//		try {
//			PreparedStatement st = conn.prepareStatement(sql);
//			ResultSet rs=st.executeQuery();
//			
//			while (rs.next()) {
//				
//			}
//			
//			
//		}catch (SQLException e) {
//			e.printStackTrace();
//			throw new RuntimeException("Errore di connessione al database");
//		}
//	}
	public List<CoppiaDirettori> getCoppiaDirettori (int anno, Map<Integer, Director> idMap) {
		String sql="SELECT md1.director_id AS d1, md2.director_id AS d2, COUNT(*) AS num "
				+ "FROM roles r1, roles r2, movies m1, movies m2, movies_directors md1, movies_directors md2 "
				+ "WHERE r1.movie_id=m1.id AND r2.movie_id=m2.id AND md1.director_id>md2.director_id AND md1.movie_id=m1.id AND md2.movie_id=m2.id AND r1.actor_id=r2.actor_id AND m1.year=? AND m2.year=? "
				+ "GROUP BY md1.director_id, md2.director_id";
		Connection conn= DBConnect.getConnection();
		List<CoppiaDirettori> result = new ArrayList<CoppiaDirettori>();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			ResultSet res=st.executeQuery();
			while (res.next()) {
				Director d1=idMap.get(res.getInt("d1"));				
				Director d2=idMap.get(res.getInt("d2"));	
				idMap.put(d1.getId(), d1);
				idMap.put(d2.getId(), d2);
				result.add(new CoppiaDirettori(d1, d2, res.getInt("num")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
		
	}
	
	public int getPeso (Director d1, Director d2) {
		String sql="SELECT COUNT(*) AS peso "
				+ "FROM movies m1, movies m2, movies_directors md1, movies_directors md2, roles a1, roles a2 "
				+ "WHERE a1.movie_id=m1.id AND a2.movie_id=m2.id AND a1.actor_id=a2.actor_id AND md1.movie_id=m1.id AND md2.movie_id=m2.id AND md1.director_id=? AND md2.director_id=?";
		Connection conn= DBConnect.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, d1.getId());
			st.setInt(2, d2.getId());
			ResultSet res=st.executeQuery();
			int peso=0;
			if (res.next()) {
				peso=res.getInt("peso");
			}
			conn.close();
			return peso;
		}catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	
	
	
	
	}
	
}
