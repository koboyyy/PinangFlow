package com.pinangflow.app.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StockDao_Impl implements StockDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StockEntryEntity> __insertionAdapterOfStockEntryEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateWeightAndStatus;

  public StockDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStockEntryEntity = new EntityInsertionAdapter<StockEntryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `stock_entries` (`id`,`petaniId`,`initialWeightKg`,`currentWeightKg`,`grade`,`condition`,`buyPricePerKg`,`entryTimestamp`,`status`,`dailyShrinkagePercentage`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StockEntryEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getPetaniId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getPetaniId());
        }
        statement.bindDouble(3, entity.getInitialWeightKg());
        statement.bindDouble(4, entity.getCurrentWeightKg());
        if (entity.getGrade() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getGrade());
        }
        if (entity.getCondition() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCondition());
        }
        statement.bindDouble(7, entity.getBuyPricePerKg());
        statement.bindLong(8, entity.getEntryTimestamp());
        if (entity.getStatus() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getStatus());
        }
        statement.bindDouble(10, entity.getDailyShrinkagePercentage());
      }
    };
    this.__preparedStmtOfUpdateWeightAndStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE stock_entries SET currentWeightKg = ?, status = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertStockEntry(final StockEntryEntity entry,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfStockEntryEntity.insert(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateWeightAndStatus(final String entryId, final double currentWeight,
      final String newStatus, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateWeightAndStatus.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, currentWeight);
        _argIndex = 2;
        if (newStatus == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, newStatus);
        }
        _argIndex = 3;
        if (entryId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, entryId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateWeightAndStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<StockEntryEntity>> getAllStockEntries() {
    final String _sql = "SELECT * FROM stock_entries";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"stock_entries"}, new Callable<List<StockEntryEntity>>() {
      @Override
      @NonNull
      public List<StockEntryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPetaniId = CursorUtil.getColumnIndexOrThrow(_cursor, "petaniId");
          final int _cursorIndexOfInitialWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "initialWeightKg");
          final int _cursorIndexOfCurrentWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "currentWeightKg");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
          final int _cursorIndexOfBuyPricePerKg = CursorUtil.getColumnIndexOrThrow(_cursor, "buyPricePerKg");
          final int _cursorIndexOfEntryTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "entryTimestamp");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDailyShrinkagePercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyShrinkagePercentage");
          final List<StockEntryEntity> _result = new ArrayList<StockEntryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StockEntryEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpPetaniId;
            if (_cursor.isNull(_cursorIndexOfPetaniId)) {
              _tmpPetaniId = null;
            } else {
              _tmpPetaniId = _cursor.getString(_cursorIndexOfPetaniId);
            }
            final double _tmpInitialWeightKg;
            _tmpInitialWeightKg = _cursor.getDouble(_cursorIndexOfInitialWeightKg);
            final double _tmpCurrentWeightKg;
            _tmpCurrentWeightKg = _cursor.getDouble(_cursorIndexOfCurrentWeightKg);
            final String _tmpGrade;
            if (_cursor.isNull(_cursorIndexOfGrade)) {
              _tmpGrade = null;
            } else {
              _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
            }
            final String _tmpCondition;
            if (_cursor.isNull(_cursorIndexOfCondition)) {
              _tmpCondition = null;
            } else {
              _tmpCondition = _cursor.getString(_cursorIndexOfCondition);
            }
            final double _tmpBuyPricePerKg;
            _tmpBuyPricePerKg = _cursor.getDouble(_cursorIndexOfBuyPricePerKg);
            final long _tmpEntryTimestamp;
            _tmpEntryTimestamp = _cursor.getLong(_cursorIndexOfEntryTimestamp);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final double _tmpDailyShrinkagePercentage;
            _tmpDailyShrinkagePercentage = _cursor.getDouble(_cursorIndexOfDailyShrinkagePercentage);
            _item = new StockEntryEntity(_tmpId,_tmpPetaniId,_tmpInitialWeightKg,_tmpCurrentWeightKg,_tmpGrade,_tmpCondition,_tmpBuyPricePerKg,_tmpEntryTimestamp,_tmpStatus,_tmpDailyShrinkagePercentage);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<StockEntryEntity>> getDryingStockEntries() {
    final String _sql = "SELECT * FROM stock_entries WHERE status = 'DRYING'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"stock_entries"}, new Callable<List<StockEntryEntity>>() {
      @Override
      @NonNull
      public List<StockEntryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPetaniId = CursorUtil.getColumnIndexOrThrow(_cursor, "petaniId");
          final int _cursorIndexOfInitialWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "initialWeightKg");
          final int _cursorIndexOfCurrentWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "currentWeightKg");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
          final int _cursorIndexOfBuyPricePerKg = CursorUtil.getColumnIndexOrThrow(_cursor, "buyPricePerKg");
          final int _cursorIndexOfEntryTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "entryTimestamp");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDailyShrinkagePercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyShrinkagePercentage");
          final List<StockEntryEntity> _result = new ArrayList<StockEntryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StockEntryEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpPetaniId;
            if (_cursor.isNull(_cursorIndexOfPetaniId)) {
              _tmpPetaniId = null;
            } else {
              _tmpPetaniId = _cursor.getString(_cursorIndexOfPetaniId);
            }
            final double _tmpInitialWeightKg;
            _tmpInitialWeightKg = _cursor.getDouble(_cursorIndexOfInitialWeightKg);
            final double _tmpCurrentWeightKg;
            _tmpCurrentWeightKg = _cursor.getDouble(_cursorIndexOfCurrentWeightKg);
            final String _tmpGrade;
            if (_cursor.isNull(_cursorIndexOfGrade)) {
              _tmpGrade = null;
            } else {
              _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
            }
            final String _tmpCondition;
            if (_cursor.isNull(_cursorIndexOfCondition)) {
              _tmpCondition = null;
            } else {
              _tmpCondition = _cursor.getString(_cursorIndexOfCondition);
            }
            final double _tmpBuyPricePerKg;
            _tmpBuyPricePerKg = _cursor.getDouble(_cursorIndexOfBuyPricePerKg);
            final long _tmpEntryTimestamp;
            _tmpEntryTimestamp = _cursor.getLong(_cursorIndexOfEntryTimestamp);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final double _tmpDailyShrinkagePercentage;
            _tmpDailyShrinkagePercentage = _cursor.getDouble(_cursorIndexOfDailyShrinkagePercentage);
            _item = new StockEntryEntity(_tmpId,_tmpPetaniId,_tmpInitialWeightKg,_tmpCurrentWeightKg,_tmpGrade,_tmpCondition,_tmpBuyPricePerKg,_tmpEntryTimestamp,_tmpStatus,_tmpDailyShrinkagePercentage);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<StockEntryEntity>> getReadyStockEntriesAsc() {
    final String _sql = "SELECT * FROM stock_entries WHERE status = 'READY' ORDER BY entryTimestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"stock_entries"}, new Callable<List<StockEntryEntity>>() {
      @Override
      @NonNull
      public List<StockEntryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPetaniId = CursorUtil.getColumnIndexOrThrow(_cursor, "petaniId");
          final int _cursorIndexOfInitialWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "initialWeightKg");
          final int _cursorIndexOfCurrentWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "currentWeightKg");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
          final int _cursorIndexOfBuyPricePerKg = CursorUtil.getColumnIndexOrThrow(_cursor, "buyPricePerKg");
          final int _cursorIndexOfEntryTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "entryTimestamp");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDailyShrinkagePercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyShrinkagePercentage");
          final List<StockEntryEntity> _result = new ArrayList<StockEntryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StockEntryEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpPetaniId;
            if (_cursor.isNull(_cursorIndexOfPetaniId)) {
              _tmpPetaniId = null;
            } else {
              _tmpPetaniId = _cursor.getString(_cursorIndexOfPetaniId);
            }
            final double _tmpInitialWeightKg;
            _tmpInitialWeightKg = _cursor.getDouble(_cursorIndexOfInitialWeightKg);
            final double _tmpCurrentWeightKg;
            _tmpCurrentWeightKg = _cursor.getDouble(_cursorIndexOfCurrentWeightKg);
            final String _tmpGrade;
            if (_cursor.isNull(_cursorIndexOfGrade)) {
              _tmpGrade = null;
            } else {
              _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
            }
            final String _tmpCondition;
            if (_cursor.isNull(_cursorIndexOfCondition)) {
              _tmpCondition = null;
            } else {
              _tmpCondition = _cursor.getString(_cursorIndexOfCondition);
            }
            final double _tmpBuyPricePerKg;
            _tmpBuyPricePerKg = _cursor.getDouble(_cursorIndexOfBuyPricePerKg);
            final long _tmpEntryTimestamp;
            _tmpEntryTimestamp = _cursor.getLong(_cursorIndexOfEntryTimestamp);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final double _tmpDailyShrinkagePercentage;
            _tmpDailyShrinkagePercentage = _cursor.getDouble(_cursorIndexOfDailyShrinkagePercentage);
            _item = new StockEntryEntity(_tmpId,_tmpPetaniId,_tmpInitialWeightKg,_tmpCurrentWeightKg,_tmpGrade,_tmpCondition,_tmpBuyPricePerKg,_tmpEntryTimestamp,_tmpStatus,_tmpDailyShrinkagePercentage);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
