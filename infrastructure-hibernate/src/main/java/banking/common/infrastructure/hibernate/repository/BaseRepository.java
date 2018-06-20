package banking.common.infrastructure.hibernate.repository;

public interface BaseRepository<T> {
	public void persist(T entity);
	public void save(T entity);
	public void update(T entity);
	public void merge(T entity);
	public void saveOrUpdate(T entity);
}
