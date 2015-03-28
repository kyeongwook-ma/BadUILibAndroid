package sogang.selab.db;

public interface IDBCreator {
	String[] getCreateTableStmt();
	String[] getInitDataStmt();
}
