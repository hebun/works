package freela.works;

// default package
// Generated Mar 3, 2014 8:45:50 AM by Hibernate Tools 3.4.0.CR1



public class Match implements java.io.Serializable {

	private Integer id;
	private Integer site;
	private String externId;
	private String homeTeam;
	private String awayTeam;
	private Integer ht;
	private Integer at;
	private Integer draw;
	private Integer deht;
	private Integer deat;
	private String tarih;
	public Integer otherId = 0;

	public Match() {
	}

	public Match(Integer site, String externId, String homeTeam,
			String awayTeam, Integer ht, Integer at, Integer draw,
			Integer deht, Integer deat, String tarih) {
		this.site = site;
		this.externId = externId;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.ht = ht;
		this.at = at;
		this.draw = draw;
		this.deht = deht;
		this.deat = deat;
		this.tarih = tarih;
		this.otherId = 0;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getSite() {
		return this.site;
	}

	public void setSite(Integer site) {
		this.site = site;
	}


	public String getExternId() {
		return this.externId;
	}

	public void setExternId(String externId) {
		this.externId = externId;
	}


	public String getHomeTeam() {
		return this.homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}


	public String getAwayTeam() {
		return this.awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Integer getHt() {
		return this.ht;
	}

	public void setHt(Integer ht) {
		this.ht = ht;
	}

	public Integer getAt() {
		return this.at;
	}

	public void setAt(Integer at) {
		this.at = at;
	}

	public Integer getDraw() {
		return this.draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}


	public Integer getDeht() {
		return this.deht;
	}

	public void setDeht(Integer deht) {
		this.deht = deht;
	}

	
	public Integer getDeat() {
		return this.deat;
	}

	public void setDeat(Integer deat) {
		this.deat = deat;
	}


	public String getTarih() {
		return this.tarih;
	}

	public void setTarih(String tarih) {
		this.tarih = tarih;
	}

}
