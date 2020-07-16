package cn.kgc.tangcco.zwpl.common.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRang {
	private Integer pageNumber;
	private Integer pageSize;
}
