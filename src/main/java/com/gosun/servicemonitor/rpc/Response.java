/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.gosun.servicemonitor.rpc;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Response extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Response\",\"namespace\":\"com.gosun.servicemonitor.rpc\",\"fields\":[{\"name\":\"status\",\"type\":\"int\"},{\"name\":\"QTime\",\"type\":\"long\"},{\"name\":\"msg\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public int status;
  @Deprecated public long QTime;
  @Deprecated public java.lang.CharSequence msg;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public Response() {}

  /**
   * All-args constructor.
   */
  public Response(java.lang.Integer status, java.lang.Long QTime, java.lang.CharSequence msg) {
    this.status = status;
    this.QTime = QTime;
    this.msg = msg;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return status;
    case 1: return QTime;
    case 2: return msg;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: status = (java.lang.Integer)value$; break;
    case 1: QTime = (java.lang.Long)value$; break;
    case 2: msg = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'status' field.
   */
  public java.lang.Integer getStatus() {
    return status;
  }

  /**
   * Sets the value of the 'status' field.
   * @param value the value to set.
   */
  public void setStatus(java.lang.Integer value) {
    this.status = value;
  }

  /**
   * Gets the value of the 'QTime' field.
   */
  public java.lang.Long getQTime() {
    return QTime;
  }

  /**
   * Sets the value of the 'QTime' field.
   * @param value the value to set.
   */
  public void setQTime(java.lang.Long value) {
    this.QTime = value;
  }

  /**
   * Gets the value of the 'msg' field.
   */
  public java.lang.CharSequence getMsg() {
    return msg;
  }

  /**
   * Sets the value of the 'msg' field.
   * @param value the value to set.
   */
  public void setMsg(java.lang.CharSequence value) {
    this.msg = value;
  }

  /** Creates a new Response RecordBuilder */
  public static com.gosun.servicemonitor.rpc.Response.Builder newBuilder() {
    return new com.gosun.servicemonitor.rpc.Response.Builder();
  }
  
  /** Creates a new Response RecordBuilder by copying an existing Builder */
  public static com.gosun.servicemonitor.rpc.Response.Builder newBuilder(com.gosun.servicemonitor.rpc.Response.Builder other) {
    return new com.gosun.servicemonitor.rpc.Response.Builder(other);
  }
  
  /** Creates a new Response RecordBuilder by copying an existing Response instance */
  public static com.gosun.servicemonitor.rpc.Response.Builder newBuilder(com.gosun.servicemonitor.rpc.Response other) {
    return new com.gosun.servicemonitor.rpc.Response.Builder(other);
  }
  
  /**
   * RecordBuilder for Response instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Response>
    implements org.apache.avro.data.RecordBuilder<Response> {

    private int status;
    private long QTime;
    private java.lang.CharSequence msg;

    /** Creates a new Builder */
    private Builder() {
      super(com.gosun.servicemonitor.rpc.Response.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.gosun.servicemonitor.rpc.Response.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.status)) {
        this.status = data().deepCopy(fields()[0].schema(), other.status);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.QTime)) {
        this.QTime = data().deepCopy(fields()[1].schema(), other.QTime);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.msg)) {
        this.msg = data().deepCopy(fields()[2].schema(), other.msg);
        fieldSetFlags()[2] = true;
      }
    }
    
    /** Creates a Builder by copying an existing Response instance */
    private Builder(com.gosun.servicemonitor.rpc.Response other) {
            super(com.gosun.servicemonitor.rpc.Response.SCHEMA$);
      if (isValidValue(fields()[0], other.status)) {
        this.status = data().deepCopy(fields()[0].schema(), other.status);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.QTime)) {
        this.QTime = data().deepCopy(fields()[1].schema(), other.QTime);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.msg)) {
        this.msg = data().deepCopy(fields()[2].schema(), other.msg);
        fieldSetFlags()[2] = true;
      }
    }

    /** Gets the value of the 'status' field */
    public java.lang.Integer getStatus() {
      return status;
    }
    
    /** Sets the value of the 'status' field */
    public com.gosun.servicemonitor.rpc.Response.Builder setStatus(int value) {
      validate(fields()[0], value);
      this.status = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'status' field has been set */
    public boolean hasStatus() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'status' field */
    public com.gosun.servicemonitor.rpc.Response.Builder clearStatus() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'QTime' field */
    public java.lang.Long getQTime() {
      return QTime;
    }
    
    /** Sets the value of the 'QTime' field */
    public com.gosun.servicemonitor.rpc.Response.Builder setQTime(long value) {
      validate(fields()[1], value);
      this.QTime = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'QTime' field has been set */
    public boolean hasQTime() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'QTime' field */
    public com.gosun.servicemonitor.rpc.Response.Builder clearQTime() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'msg' field */
    public java.lang.CharSequence getMsg() {
      return msg;
    }
    
    /** Sets the value of the 'msg' field */
    public com.gosun.servicemonitor.rpc.Response.Builder setMsg(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.msg = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'msg' field has been set */
    public boolean hasMsg() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'msg' field */
    public com.gosun.servicemonitor.rpc.Response.Builder clearMsg() {
      msg = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public Response build() {
      try {
        Response record = new Response();
        record.status = fieldSetFlags()[0] ? this.status : (java.lang.Integer) defaultValue(fields()[0]);
        record.QTime = fieldSetFlags()[1] ? this.QTime : (java.lang.Long) defaultValue(fields()[1]);
        record.msg = fieldSetFlags()[2] ? this.msg : (java.lang.CharSequence) defaultValue(fields()[2]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
