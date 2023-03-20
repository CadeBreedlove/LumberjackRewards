import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.annotation.Native;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "LumberjackRewards.db";

    // User Table
    public static final String USER_TABLE = "User";
    public static final String USER_COLUMN_UserID = "UserID";
    public static final String USER_COLUMN_FName = "FName";
    public static final String USER_COLUMN_Lname = "LName";
    public static final String USER_COLUMN_Email = "Email";
    public static final String USER_COLUMN_Role = "Role";

    // BadgesOwned Connector Table
    public static final String BADGES_OWNED_TABLE = "BadgesOwned";
    public static final String BADGES_OWNED_ID = "BadgesOwnedID";
    //public static final String BADGES_OWNED_COLUMN_UserID = "UserID";
    //public static final String BADGES_OWNED_COLUMN_BadgeID= "BadgeID";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
