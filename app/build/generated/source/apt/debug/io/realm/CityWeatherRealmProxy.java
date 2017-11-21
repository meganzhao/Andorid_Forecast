package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CityWeatherRealmProxy extends hu.ait.android.forecast.data.CityWeather
    implements RealmObjectProxy, CityWeatherRealmProxyInterface {

    static final class CityWeatherColumnInfo extends ColumnInfo
        implements Cloneable {

        public long cityWeatherIDIndex;
        public long cityNameIndex;

        CityWeatherColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(2);
            this.cityWeatherIDIndex = getValidColumnIndex(path, table, "CityWeather", "cityWeatherID");
            indicesMap.put("cityWeatherID", this.cityWeatherIDIndex);
            this.cityNameIndex = getValidColumnIndex(path, table, "CityWeather", "cityName");
            indicesMap.put("cityName", this.cityNameIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final CityWeatherColumnInfo otherInfo = (CityWeatherColumnInfo) other;
            this.cityWeatherIDIndex = otherInfo.cityWeatherIDIndex;
            this.cityNameIndex = otherInfo.cityNameIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final CityWeatherColumnInfo clone() {
            return (CityWeatherColumnInfo) super.clone();
        }

    }
    private CityWeatherColumnInfo columnInfo;
    private ProxyState<hu.ait.android.forecast.data.CityWeather> proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("cityWeatherID");
        fieldNames.add("cityName");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    CityWeatherRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (CityWeatherColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<hu.ait.android.forecast.data.CityWeather>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @SuppressWarnings("cast")
    public String realmGet$cityWeatherID() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.cityWeatherIDIndex);
    }

    public void realmSet$cityWeatherID(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'cityWeatherID' cannot be changed after object was created.");
    }

    @SuppressWarnings("cast")
    public String realmGet$cityName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.cityNameIndex);
    }

    public void realmSet$cityName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.cityNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.cityNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.cityNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.cityNameIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("CityWeather")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("CityWeather");
            realmObjectSchema.add(new Property("cityWeatherID", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("cityName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            return realmObjectSchema;
        }
        return realmSchema.get("CityWeather");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_CityWeather")) {
            Table table = sharedRealm.getTable("class_CityWeather");
            table.addColumn(RealmFieldType.STRING, "cityWeatherID", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "cityName", Table.NULLABLE);
            table.addSearchIndex(table.getColumnIndex("cityWeatherID"));
            table.setPrimaryKey("cityWeatherID");
            return table;
        }
        return sharedRealm.getTable("class_CityWeather");
    }

    public static CityWeatherColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_CityWeather")) {
            Table table = sharedRealm.getTable("class_CityWeather");
            final long columnCount = table.getColumnCount();
            if (columnCount != 2) {
                if (columnCount < 2) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 2 but was " + columnCount);
                }
                if (allowExtraColumns) {
                    RealmLog.debug("Field count is more than expected - expected 2 but was %1$d", columnCount);
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 2 but was " + columnCount);
                }
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < columnCount; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final CityWeatherColumnInfo columnInfo = new CityWeatherColumnInfo(sharedRealm.getPath(), table);

            if (!table.hasPrimaryKey()) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary key not defined for field 'cityWeatherID' in existing Realm file. @PrimaryKey was added.");
            } else {
                if (table.getPrimaryKey() != columnInfo.cityWeatherIDIndex) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key annotation definition was changed, from field " + table.getColumnName(table.getPrimaryKey()) + " to field cityWeatherID");
                }
            }

            if (!columnTypes.containsKey("cityWeatherID")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'cityWeatherID' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("cityWeatherID") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'cityWeatherID' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.cityWeatherIDIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(),"@PrimaryKey field 'cityWeatherID' does not support null values in the existing Realm file. Migrate using RealmObjectSchema.setNullable(), or mark the field as @Required.");
            }
            if (!table.hasSearchIndex(table.getColumnIndex("cityWeatherID"))) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'cityWeatherID' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
            }
            if (!columnTypes.containsKey("cityName")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'cityName' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("cityName") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'cityName' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.cityNameIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'cityName' is required. Either set @Required to field 'cityName' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'CityWeather' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_CityWeather";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static hu.ait.android.forecast.data.CityWeather createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        hu.ait.android.forecast.data.CityWeather obj = null;
        if (update) {
            Table table = realm.getTable(hu.ait.android.forecast.data.CityWeather.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = Table.NO_MATCH;
            if (json.isNull("cityWeatherID")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("cityWeatherID"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(hu.ait.android.forecast.data.CityWeather.class), false, Collections.<String> emptyList());
                    obj = new io.realm.CityWeatherRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("cityWeatherID")) {
                if (json.isNull("cityWeatherID")) {
                    obj = (io.realm.CityWeatherRealmProxy) realm.createObjectInternal(hu.ait.android.forecast.data.CityWeather.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.CityWeatherRealmProxy) realm.createObjectInternal(hu.ait.android.forecast.data.CityWeather.class, json.getString("cityWeatherID"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'cityWeatherID'.");
            }
        }
        if (json.has("cityName")) {
            if (json.isNull("cityName")) {
                ((CityWeatherRealmProxyInterface) obj).realmSet$cityName(null);
            } else {
                ((CityWeatherRealmProxyInterface) obj).realmSet$cityName((String) json.getString("cityName"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static hu.ait.android.forecast.data.CityWeather createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        hu.ait.android.forecast.data.CityWeather obj = new hu.ait.android.forecast.data.CityWeather();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("cityWeatherID")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CityWeatherRealmProxyInterface) obj).realmSet$cityWeatherID(null);
                } else {
                    ((CityWeatherRealmProxyInterface) obj).realmSet$cityWeatherID((String) reader.nextString());
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("cityName")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CityWeatherRealmProxyInterface) obj).realmSet$cityName(null);
                } else {
                    ((CityWeatherRealmProxyInterface) obj).realmSet$cityName((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'cityWeatherID'.");
        }
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static hu.ait.android.forecast.data.CityWeather copyOrUpdate(Realm realm, hu.ait.android.forecast.data.CityWeather object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (hu.ait.android.forecast.data.CityWeather) cachedRealmObject;
        } else {
            hu.ait.android.forecast.data.CityWeather realmObject = null;
            boolean canUpdate = update;
            if (canUpdate) {
                Table table = realm.getTable(hu.ait.android.forecast.data.CityWeather.class);
                long pkColumnIndex = table.getPrimaryKey();
                String value = ((CityWeatherRealmProxyInterface) object).realmGet$cityWeatherID();
                long rowIndex = Table.NO_MATCH;
                if (value == null) {
                    rowIndex = table.findFirstNull(pkColumnIndex);
                } else {
                    rowIndex = table.findFirstString(pkColumnIndex, value);
                }
                if (rowIndex != Table.NO_MATCH) {
                    try {
                        objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(hu.ait.android.forecast.data.CityWeather.class), false, Collections.<String> emptyList());
                        realmObject = new io.realm.CityWeatherRealmProxy();
                        cache.put(object, (RealmObjectProxy) realmObject);
                    } finally {
                        objectContext.clear();
                    }
                } else {
                    canUpdate = false;
                }
            }

            if (canUpdate) {
                return update(realm, realmObject, object, cache);
            } else {
                return copy(realm, object, update, cache);
            }
        }
    }

    public static hu.ait.android.forecast.data.CityWeather copy(Realm realm, hu.ait.android.forecast.data.CityWeather newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (hu.ait.android.forecast.data.CityWeather) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            hu.ait.android.forecast.data.CityWeather realmObject = realm.createObjectInternal(hu.ait.android.forecast.data.CityWeather.class, ((CityWeatherRealmProxyInterface) newObject).realmGet$cityWeatherID(), false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((CityWeatherRealmProxyInterface) realmObject).realmSet$cityName(((CityWeatherRealmProxyInterface) newObject).realmGet$cityName());
            return realmObject;
        }
    }

    public static long insert(Realm realm, hu.ait.android.forecast.data.CityWeather object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(hu.ait.android.forecast.data.CityWeather.class);
        long tableNativePtr = table.getNativeTablePointer();
        CityWeatherColumnInfo columnInfo = (CityWeatherColumnInfo) realm.schema.getColumnInfo(hu.ait.android.forecast.data.CityWeather.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((CityWeatherRealmProxyInterface) object).realmGet$cityWeatherID();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = table.addEmptyRowWithPrimaryKey(primaryKeyValue, false);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$cityName = ((CityWeatherRealmProxyInterface)object).realmGet$cityName();
        if (realmGet$cityName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.cityNameIndex, rowIndex, realmGet$cityName, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(hu.ait.android.forecast.data.CityWeather.class);
        long tableNativePtr = table.getNativeTablePointer();
        CityWeatherColumnInfo columnInfo = (CityWeatherColumnInfo) realm.schema.getColumnInfo(hu.ait.android.forecast.data.CityWeather.class);
        long pkColumnIndex = table.getPrimaryKey();
        hu.ait.android.forecast.data.CityWeather object = null;
        while (objects.hasNext()) {
            object = (hu.ait.android.forecast.data.CityWeather) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                String primaryKeyValue = ((CityWeatherRealmProxyInterface) object).realmGet$cityWeatherID();
                long rowIndex = Table.NO_MATCH;
                if (primaryKeyValue == null) {
                    rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
                } else {
                    rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
                }
                if (rowIndex == Table.NO_MATCH) {
                    rowIndex = table.addEmptyRowWithPrimaryKey(primaryKeyValue, false);
                } else {
                    Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
                }
                cache.put(object, rowIndex);
                String realmGet$cityName = ((CityWeatherRealmProxyInterface)object).realmGet$cityName();
                if (realmGet$cityName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.cityNameIndex, rowIndex, realmGet$cityName, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, hu.ait.android.forecast.data.CityWeather object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(hu.ait.android.forecast.data.CityWeather.class);
        long tableNativePtr = table.getNativeTablePointer();
        CityWeatherColumnInfo columnInfo = (CityWeatherColumnInfo) realm.schema.getColumnInfo(hu.ait.android.forecast.data.CityWeather.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((CityWeatherRealmProxyInterface) object).realmGet$cityWeatherID();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = table.addEmptyRowWithPrimaryKey(primaryKeyValue, false);
        }
        cache.put(object, rowIndex);
        String realmGet$cityName = ((CityWeatherRealmProxyInterface)object).realmGet$cityName();
        if (realmGet$cityName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.cityNameIndex, rowIndex, realmGet$cityName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.cityNameIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(hu.ait.android.forecast.data.CityWeather.class);
        long tableNativePtr = table.getNativeTablePointer();
        CityWeatherColumnInfo columnInfo = (CityWeatherColumnInfo) realm.schema.getColumnInfo(hu.ait.android.forecast.data.CityWeather.class);
        long pkColumnIndex = table.getPrimaryKey();
        hu.ait.android.forecast.data.CityWeather object = null;
        while (objects.hasNext()) {
            object = (hu.ait.android.forecast.data.CityWeather) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                String primaryKeyValue = ((CityWeatherRealmProxyInterface) object).realmGet$cityWeatherID();
                long rowIndex = Table.NO_MATCH;
                if (primaryKeyValue == null) {
                    rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
                } else {
                    rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
                }
                if (rowIndex == Table.NO_MATCH) {
                    rowIndex = table.addEmptyRowWithPrimaryKey(primaryKeyValue, false);
                }
                cache.put(object, rowIndex);
                String realmGet$cityName = ((CityWeatherRealmProxyInterface)object).realmGet$cityName();
                if (realmGet$cityName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.cityNameIndex, rowIndex, realmGet$cityName, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.cityNameIndex, rowIndex, false);
                }
            }
        }
    }

    public static hu.ait.android.forecast.data.CityWeather createDetachedCopy(hu.ait.android.forecast.data.CityWeather realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        hu.ait.android.forecast.data.CityWeather unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (hu.ait.android.forecast.data.CityWeather)cachedObject.object;
            } else {
                unmanagedObject = (hu.ait.android.forecast.data.CityWeather)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new hu.ait.android.forecast.data.CityWeather();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((CityWeatherRealmProxyInterface) unmanagedObject).realmSet$cityWeatherID(((CityWeatherRealmProxyInterface) realmObject).realmGet$cityWeatherID());
        ((CityWeatherRealmProxyInterface) unmanagedObject).realmSet$cityName(((CityWeatherRealmProxyInterface) realmObject).realmGet$cityName());
        return unmanagedObject;
    }

    static hu.ait.android.forecast.data.CityWeather update(Realm realm, hu.ait.android.forecast.data.CityWeather realmObject, hu.ait.android.forecast.data.CityWeather newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ((CityWeatherRealmProxyInterface) realmObject).realmSet$cityName(((CityWeatherRealmProxyInterface) newObject).realmGet$cityName());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("CityWeather = [");
        stringBuilder.append("{cityWeatherID:");
        stringBuilder.append(realmGet$cityWeatherID() != null ? realmGet$cityWeatherID() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{cityName:");
        stringBuilder.append(realmGet$cityName() != null ? realmGet$cityName() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityWeatherRealmProxy aCityWeather = (CityWeatherRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aCityWeather.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aCityWeather.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aCityWeather.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
