package me.factories;

public abstract class Builder<T> {  
	private String typeTag;  //say the king of builder it is, a.k.a. the T variable value
	private String desc; //to say what the builders does

	public Builder(String typeTag, String desc) throws IllegalArgumentException {
    if (typeTag == null || desc == null || typeTag.isBlank() || desc.isBlank())  
			throw new IllegalArgumentException("Invalid type/desc");  
		this.typeTag = typeTag;  
		this.desc = desc;
	}

	public String getTypeTag() {  
		return typeTag;  
	}

	public String getInfo() {  
		return null;
	}

	abstract void fillInData(JSONObject o); // subclasses will override this method 

	@Override  
	public String toString() {  
		return desc;  
	}

	protected abstract T createInstance(JSONObject data);  //this is the real important method where the classes are created
	
}
