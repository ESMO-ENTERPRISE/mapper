package com.esmo.mapper.example.ex16;

import com.esmo.mapper.example.ex15.AbstractData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Data2b extends AbstractData {
	private String d1_a = null;
	private String d1_b = null;
	private String d1_c = null;


	static public Data2b createOutData2() {
		Data2b outD2 = AbstractData.random(new Data2b());
		outD2.setD1_a("val" + i++);
		outD2.setD1_b("val" + i++);
		outD2.setD1_c("val" + i++);
		return outD2;
	}
}
