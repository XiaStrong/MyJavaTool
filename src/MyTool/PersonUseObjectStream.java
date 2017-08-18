package MyTool;

import java.io.Serializable;
/**
 * 
 * @author XQ
 *	序列化对象需要实现Serializable接口
 */
public class PersonUseObjectStream implements Serializable{
	/**
	 * serialVersionUID:序列化版本id
	 * 作用：就序列化版本号，解决序列化兼容问题
	 * transient：属性不想被序列化，那么就使用这个修饰符修饰，读取时不会保存原有数据
	 */
	private static final long serialVersionUID = 658631278628871592L;
	private String name;
	private int age;
	private transient double salary;
	public PersonUseObjectStream(String name, int age, double salary) {
		super();
		this.name = name;
		this.age = age;
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "PersonUseObjectStream [name=" + name + ", age=" + age + ", salary=" + salary + "]";
	}
	public PersonUseObjectStream() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
}
