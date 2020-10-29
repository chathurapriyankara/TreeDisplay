package application;

import java.util.ArrayDeque;
import java.util.Queue;

public class PersonBST {

	Person root;

	public void addPerson(Person newPerson) {
		if (root == null) {
			root = newPerson;
			return;
		}
		addPerson(root, newPerson);
	}

	private void addPerson(Person current, Person newPerson) {
		if (current == null) {
			return;
		}
		if (compareNames(current.getFirstName(), newPerson.getFirstName()) > 0) {
			if (current.getBefore() == null) {
				current.setBefore(newPerson);
			} else {
				addPerson(current.getBefore(), newPerson);
			}
		} else if (compareNames(current.getFirstName(), newPerson.getFirstName()) < 0) {
			if (current.getAfter() == null) {
				current.setAfter(newPerson);
			} else {
				addPerson(current.getAfter(), newPerson);
			}
		}
	}

	public void breadthFirstTraversal(Person root) {
		Queue<Person> todo = new ArrayDeque<>();
		todo.offer(root);

		while (!todo.isEmpty()) {
			int levelNodes = todo.size();
			while (levelNodes > 0) {
				Person p = todo.poll();
				System.out.print(p.getFirstName() + " ");
				if(p.getBefore() != null) {
					System.out.print(p.getBefore().getFirstName());
				}
				if (p.getBefore() != null) {
					todo.offer(p.getBefore());
				}
				if (p.getAfter() != null) {
					todo.offer(p.getAfter());
				}
				levelNodes--;
			}
			System.out.println();
		}

	}
	
	public int compareNames(String str1, String str2) {
		return str1.compareTo(str2);
	}

	public void preorder(Person person) {
		if (person == null) {
			return;
		}
		System.out.println(person.getFirstName());
		preorder(person.getBefore());
		preorder(person.getAfter());
	}

}
