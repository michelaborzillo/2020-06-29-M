package it.polito.tdp.imdb.model;

public class Director implements Comparable<Director>{
	Integer id;
	String firstName;
	String lastName;
public int peso;
	public Director(Integer id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return this.getId()+" - "+this.getFirstName()+" "+this.getLastName();
	}

	@Override
	public int compareTo(Director arg0) {
		
		return this.getId().compareTo(arg0.getId());
	}

	public String toString1() {
		// TODO Auto-generated method stub
		return this.getId()+" - "+this.getFirstName()+" - "+this.getLastName()+" - "+this.peso;
	}

	public void setAdiacenza(int pesoGrafo) {
		this.peso=pesoGrafo;
		
	}



	
	
	
}
