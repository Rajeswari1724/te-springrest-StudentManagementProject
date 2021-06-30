package com.te.student.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.te.student.beans.StudentBean;

@Repository
public class StudentDaoImpl implements StudentDao {

	@PersistenceUnit
	private EntityManagerFactory factory;

	@Override
	public StudentBean getStudent(double marks) {
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("springdb");
		EntityManager entityManager = factory.createEntityManager();

		StudentBean studentBean = entityManager.find(StudentBean.class, marks);
				return studentBean;

	}

	@Override
	public boolean deleteStuData(double marks) {
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("springdb");
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			StudentBean bean = entityManager.find(StudentBean.class, marks);
			if (bean != null) {
				entityManager.remove(bean);
				transaction.commit();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
	}

	@Override
	public List<StudentBean> getAllStu() {
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("springdb");
		EntityManager entityManager = factory.createEntityManager();
        
		String query = " from StudentBean";
		Query query2 = entityManager.createQuery(query);

		List<StudentBean> allStuData = query2.getResultList();

		return allStuData;

	}

	@Override
	public boolean addStudent(StudentBean bean) {
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("springdb");
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		boolean isAdded = false;		
		try {
			transaction.begin();
			double marks=bean.getMarks();
			if(marks>=9.1&&marks<=10.00) {
				bean.setGrade("A+");
			}
			else if(marks>=8.1&&marks<=9.00) {
				bean.setGrade("A");
			}
			else if(marks>=7.1&&marks<=8.00){
				bean.setGrade("B");
			}
			else if(marks>=6.1&&marks<=7.00) {
				bean.setGrade("C");
			}
			else if(marks>=5.1&&marks<=6.0) {
				bean.setGrade("D");
			}
			else if(marks>=4.1&&marks<=5.0) {
				bean.setGrade("E");
			}
			else if(marks<=4.0) {
				bean.setGrade("Fail");
			}
			entityManager.persist(bean);

			if (bean != null) {
				transaction.commit();
				isAdded = true;
			}
		} catch (Exception e) {
			isAdded = false;
			e.printStackTrace();
		}
		
		return isAdded;
	}

	@Override
	public boolean updateStudent(StudentBean bean) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		boolean isUpdated = false;

		try {
			transaction.begin();
			StudentBean info = manager.find(StudentBean.class, bean.getMarks());
			
			
			if (bean.getEmail() != null && bean.getEmail() != "") {
				info.setEmail(bean.getEmail());
			}
			
			
            transaction.commit();
			isUpdated = true;
		} catch (Exception e) {

			e.printStackTrace();
		}

		return isUpdated;
	}

}

