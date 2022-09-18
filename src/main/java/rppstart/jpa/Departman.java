 package rppstart.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the departman database table.
 * 
 */
//svaki objekat koji vrati backend a koje je tipa fakultet nece imati u sebi obelezja hibernate i hendler
@JsonIgnoreProperties({"hibernateLazyInitializer", "hendler"}) //izbacuje json objekat koji se pojavljuje u kolonama
@Entity
@Table(name="departman")
@NamedQuery(name="Departman.findAll", query="SELECT d FROM Departman d")
public class Departman implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DEPARTMAN_ID_GENERATOR", sequenceName="DEPARTMAN_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DEPARTMAN_ID_GENERATOR")
	private Integer id;

	private String naziv;

	private String oznaka;

	//bi-directional many-to-one association to Student
	@JsonIgnore //ovo se radi samo kod manyToOne
	@OneToMany(mappedBy="departman")
	private List<Student> students;

	//bi-directional many-to-one association to Fakultet
	@ManyToOne
	@JoinColumn(name="fakultet")
	private Fakultet fakultet;

	public Departman() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOznaka() {
		return this.oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public List<Student> getStudents() {
		return this.students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Student addStudent(Student student) {
		getStudents().add(student);
		student.setDepartman(this);

		return student;
	}

	public Student removeStudent(Student student) {
		getStudents().remove(student);
		student.setDepartman(null);

		return student;
	}

	public Fakultet getFakultet() {
		return this.fakultet;
	}

	public void setFakultet(Fakultet fakultet) {
		this.fakultet = fakultet;
	}

}