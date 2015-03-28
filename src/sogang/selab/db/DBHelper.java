package sogang.selab.db;

import sogang.selab.BadUI;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper  {

	private static final String KEY_COLUMN = "_id";
	public static final String DB_NAME = "BadUIDB";
	private static DBHelper mInstance;

	private static SQLiteDatabase mDb;

	static {
		mInstance = new DBHelper();
	}
	
	private DBHelper() {
		super(BadUI.getContext(), DB_NAME, null, 1);
	}

	public static DBHelper getInstance() {
		if (mInstance == null) {
			mInstance = new DBHelper();
		} 			
		
		mDb = mInstance.getReadableDatabase();

		return mInstance;
	}

	public void close() {
		if (mInstance != null) {
			mDb.close();
			mInstance = null;
		}
	}


	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		createDB(new BadUIDBCreator(), sqLiteDatabase);
	}


	private void createDB(IDBCreator dbCreator, SQLiteDatabase db) {
		String[] tableCreateStmt = dbCreator.getCreateTableStmt();

		if (tableCreateStmt != null && tableCreateStmt.length > 0) {
			for (String stmt : tableCreateStmt) {
				db.execSQL(stmt);
			}
		}
	}

	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

	}

	public Cursor getAllItem(String table, String[] columns) {
		return mDb.query(table, columns, null, null, null, null, null);
	}

	public Cursor get(String table, String[] columns, long id) {
		Cursor cursor = mDb.query(true, table, columns, KEY_COLUMN + "=" + id, null, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		return cursor;
	}

	public Cursor get(String sql) {
		return mDb.rawQuery(sql, null);
	}

	public long insert(String table, ContentValues values) {
		return mDb.insert(table, null, values); 
	}

	public long replace(String table, String nullColumnHack, ContentValues values) {
		return mDb.replace(table, nullColumnHack, values);
	}

	public int update(String table, ContentValues values, long id) {
		return mDb.update(table, values, KEY_COLUMN + "=" + id, null);
	}

	public Cursor getAllColumns(String table) {
		return mDb.query(table, null, null, null, null, null, null);
	}
	

	public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
		return mDb.update(table, values, whereClause, whereArgs);
	}

	public int delete(String table, String whereCaluse) {
		return mDb.delete(table, whereCaluse, null);
	}

	public int delete(String table, long id) {
		return mDb.delete(table, KEY_COLUMN + "=" + id, null);
	}

	public void exec(String sql) {
		mDb.execSQL(sql);
	}

}

