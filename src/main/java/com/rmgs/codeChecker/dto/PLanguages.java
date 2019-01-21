package com.rmgs.codeChecker.dto;

import java.util.ArrayList;
import java.util.List;

public class PLanguages {
	public PLanguages() {
		super();
	}

	String lang;
	String langName;

	public PLanguages(String lang, String langName) {
		super();
		this.lang = lang;
		this.langName = langName;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLangName() {
		return langName;
	}

	public void setLangName(String langName) {
		this.langName = langName;
	}

	public static List<PLanguages> getAllLanguage() {

		List<PLanguages> pLanguages = new ArrayList<>();
		pLanguages.add(new PLanguages("cs", "C#"));
		pLanguages.add(new PLanguages("flex", "Flex"));
		pLanguages.add(new PLanguages("java", "Java"));
		pLanguages.add(new PLanguages("js", "JavaScript"));
		pLanguages.add(new PLanguages("php", "PHP"));
		pLanguages.add(new PLanguages("py", "Python"));
		pLanguages.add(new PLanguages("ts", "TypeScript"));
		pLanguages.add(new PLanguages("xml", "XML"));
		return pLanguages;

	}
}
