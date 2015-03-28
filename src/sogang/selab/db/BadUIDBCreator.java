package sogang.selab.db;

public class BadUIDBCreator implements IDBCreator {

	@Override
	public String[] getCreateTableStmt() {
		
		final String SEQ_TABLE_CREATE_STMT = "CREATE TABLE "
				+ SeqDBscheme.TABLE_NAME + " ("
				+ SeqDBscheme.COLUMN_TIME + " INTEGER, "
				+ SeqDBscheme.COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY);";
		
		final String BM_TABLE_CREATE_STMT = "CREATE TABLE "
				+ BMDBscheme.TABLE_NAME + " ("
				+ BMDBscheme.COLUMN_ID + " INTEGER, " 
				+ BMDBscheme.COLUMN_CLASS + " TEXT, "
                + BMDBscheme.COLUMN_MODE + " TEXT, "
                + BMDBscheme.COLUMN_TIMESTAMP + " INTEGER, "
                + BMDBscheme.COLUMN_X + " INTEGER, "
                + BMDBscheme.COLUMN_Y + " INTEGER, "
				+ "FOREIGN KEY (" + BMDBscheme.COLUMN_ID + ") "
				+ "REFERENCES " + SeqDBscheme.TABLE_NAME + "(" + SeqDBscheme.COLUMN_ID + ") );" ;


		return new String[]{ SEQ_TABLE_CREATE_STMT, BM_TABLE_CREATE_STMT };
	}

	@Override
	public String[] getInitDataStmt() {
		return null;
	}

}
