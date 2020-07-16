package cn.kgc.tangcco.zwpl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFeedBack {
	
		//id
	private int id;
		//姓名
	private String name;
		//电话
	private String phone;
		//邮箱
	private String mailbox;
		//主题
	private String theme;
		//反馈内容
	private String feedback;
	
	
	public CustomerFeedBack(String name, String phone, String mailbox, String theme, String feedback) {
		super();
		this.name = name;
		this.phone = phone;
		this.mailbox = mailbox;
		this.theme = theme;
		this.feedback = feedback;
	}
}
