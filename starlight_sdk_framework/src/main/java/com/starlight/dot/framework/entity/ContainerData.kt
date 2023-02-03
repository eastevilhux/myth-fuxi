package com.starlight.dot.framework.entity

class ContainerData{
    private val map : HashMap<String,Any>;

    private constructor(builder : Builder){
        map = builder.map;
    }

    fun getInt(key : String,defaultValue : Int) : Int{
        val v = map.get(key);
        if(v is Int){
            return v;
        }
        return defaultValue;
    }

    fun getString(key : String,defaultValue : String): String {
        val v = map.get(key);
        if(v is String){
            return v;
        }
        return defaultValue;
    }

    fun getLong(key : String,defaultVlaue : Long): Long {
        val v = map.get(key);
        if(v is Long){
            return v;
        }
        return defaultVlaue;
    }

    fun getDouble(key : String,defaultValue: Double): Double {
        val v = map.get(key);
        if(v is Double){
            return v;
        }
        return defaultValue;
    }

    fun getFloat(key : String,defaultValue: Float): Float {
        val v = map.get(key);
        if(v is Float){
            return v;
        }
        return defaultValue;
    }

    fun getInt(key : String): Int {
        return getInt(key,0);
    }

    fun getDouble(key : String): Double {
        return getDouble(key,0.0);
    }

    fun getLong(key : String): Long {
        return getLong(key,0L);
    }

    fun getFloat(key : String): Float {
        return getFloat(key,0F);
    }

    fun getString(key : String): String {
        return getString(key,"");
    }

    fun get(key : String): Any? {
        return map.get(key);
    }

    fun get(key : String,defaultValue: Any): Any {
        val v = map.get(key);
        if(v != null){
            return v;
        }
        return defaultValue;
    }

    fun keys(): MutableSet<String> {
        return map.keys;
    }

    class Builder{
        internal val map : HashMap<String,Any>;

        constructor(){
            map = HashMap();
        }

        fun put(key : String,value : Int): Builder {
            map.put(key,value);
            return this;
        }

        fun put(key : String,value : String): Builder {
            map.put(key,value);
            return this;
        }

        fun put(key : String,value : Long): Builder {
            map.put(key,value);
            return this;
        }

        fun put(key : String,value : Double): Builder {
            map.put(key,value);
            return this;
        }

        fun put(key : String,value : Float): Builder {
            map.put(key,value);
            return this;
        }

        fun builder(): ContainerData {
            return ContainerData(this);
        }
    }
}
