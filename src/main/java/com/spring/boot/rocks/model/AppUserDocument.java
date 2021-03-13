package com.spring.boot.rocks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files")
public class AppUserDocument extends Auditable<String>  {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "filename")
	private String fileName;

	@Column(name = "filetype")
	private String fileType;
	
	@Column(name = "filesize")
	private long fileSize;

	@Lob
	@Column(name = "filedata")
	private byte[] data;
	
	@Version
    private Integer version;


	public AppUserDocument(String fileName, String fileType, long fileSize, byte[] data) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.data = data;
	}

}
