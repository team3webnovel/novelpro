package com.team3webnovel.vo;

public class GenreVo {
	private String code;
    private String name;

    
    
    public GenreVo() {
	}

	public GenreVo(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "GenreVo [code=" + code + ", name=" + name + "]";
	}
    
    
    
}
