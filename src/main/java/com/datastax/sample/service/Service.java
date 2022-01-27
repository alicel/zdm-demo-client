package com.datastax.sample.service;

import java.util.List;
import java.util.Random;

import com.datastax.demo.utils.PropertyHelper;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.sample.dao.SampleDao;

public class Service {

	private SampleDao dao;

	public Service() {		
		String contactPointsStr = PropertyHelper.getProperty("contactPoints", "localhost");
		this.dao = new SampleDao(contactPointsStr.split(","));
	}

	public void insertRows(Integer startKey, Integer numberOfRows) {
		for ( int i = 0; i < numberOfRows; i++ ) {
			Integer rowKey = startKey + i;
			dao.insertRow(rowKey, "I am " + generateRandomName() + " from row " + rowKey);
		}
	}

	public String selectRowValueByKey(Integer rowKey) {
		String rowValue = dao.selectRowValue(rowKey);
		return rowValue != null ? rowValue : ("No row with key " + rowKey + " was found");
	}
	
	private String generateRandomName() {

		String[] surnames = {"Smith","Johnson","Williams","Brown","Jones","Miller","Davis","Garcia","Rodriguez","Wilson",
				"Martinez","Anderson","Taylor","Thomas","Hernandez","Moore","Martin","Jackson","Thompson","White","Lopez",
				"Lee","Gonzalez","Harris","Clark","Lewis","Robinson","Walker","Perez","Hall","Young","Allen","Sanchez","Wright",
				"King","Scott","Green","Baker","Adams","Nelson","Hill","Ramirez","Campbell","Mitchell","Roberts","Carter",
				"Phillips","Evans","Turner","Torres","Parker", "Collins","Edwards","Stewart","Flores","Morris","Nguyen",
				"Murphy","Rivera","Cook","Rogers","Morgan","Peterson","Cooper","Reed","Bailey","Bell","Gomez","Kelly","Howard",
				"Ward","Cox","Diaz","Richardson","Wood","Watson","Brooks","Bennett","Gray",	"James","Reyes","Cruz","Hughes",
				"Price","Myers","Long","Foster","Sanders","Ross","Morales","Powell","Sullivan","Russell","Ortiz","Jenkins",
				"Gutierrez","Perry","Butler","Barnes","Fisher","Henderson","Coleman","Simmons","Patterson","Jordan","Reynolds",
				"Hamilton","Graham","Kim","Gonzales","Alexander","Ramos","Wallace","Griffin","West","Cole","Hayes","Chavez",
				"Gibson","Bryant","Ellis","Stevens","Murray","Ford","Marshall","Owens","Mcdonald","Harrison","Ruiz","Kennedy",
				"Wells","Alvarez","Woods","Mendoza","Castillo","Olson","Webb","Washington","Tucker","Freeman","Burns","Henry",
				"Vasquez","Snyder","Simpson","Crawford","Jimenez","Porter","Mason","Shaw","Gordon","Wagner","Hunter","Romero",
				"Hicks","Dixon","Hunt","Palmer","Robertson","Black","Holmes","Stone","Meyer","Boyd","Mills","Warren","Fox",
				"Rose","Rice","Moreno","Schmidt","Patel","Ferguson","Nichols","Herrera","Medina","Ryan","Fernandez","Weaver",
				"Daniels","Stephens","Gardner","Payne","Kelley","Dunn","Pierce","Arnold","Tran","Spencer","Peters","Hawkins",
				"Grant","Hansen","Castro","Hoffman","Hart","Elliott","Cunningham","Knight","Bradley","Carroll","Hudson","Duncan",
				"Armstrong","Berry","Andrews","Johnston","Ray","Lane","Riley","Carpenter","Perkins","Aguilar","Silva","Richards",
				"Willis","Matthews","Chapman","Lawrence","Garza","Vargas","Watkins","Wheeler","Larson","Carlson","Harper",
				"Greene","Burke","Guzman","Morrison","Munoz","Jacobs","Obrien","Lawson","Franklin","Lynch","Bishop","Carr",
				"Salazar","Austin","Mendez","Gilbert","Jensen","Williamson","Montgomery","Harvey","Oliver","Howell","Dean",
				"Hanson","Weber","Garrett","Sims","Burton","Fuller","Soto","Mccoy","Welch","Chen","Schultz","Walters","Reid",
				"Fields","Walsh","Little","Fowler","Bowman","Davidson","May","Day","Schneider","Newman","Brewer"};

		String[] names= {"James","John","Robert","Michael","William","David","Richard","Charles","Joseph","Thomas","Christopher",
				"Daniel","Paul","Mark","Donald","George","Kenneth","Steven","Edward","Brian","Ronald","Anthony","Kevin","Jason",
				"Matthew","Gary","Timothy","Jose","Larry","Jeffrey","Frank","Scott","Eric","Stephen","Andrew","Raymond","Gregory",
				"Joshua","Jerry","Dennis","Walter","Patrick","Peter","Harold","Douglas","Henry","Carl","Arthur","Ryan","Roger",
				"Joe","Juan","Jack","Albert","Jonathan","Justin","Terry","Gerald","Keith","Samuel","Willie","Ralph","Lawrence",
				"Nicholas","Roy","Benjamin","Bruce","Brandon","Adam","Harry","Fred","Wayne","Billy","Steve","Louis","Jeremy",
				"Aaron","Randy","Howard","Eugene","Carlos","Russell","Bobby","Victor","Martin","Ernest","Phillip","Todd",
				"Jesse","Craig","Alan","Shawn","Clarence","Sean","Philip","Chris","Johnny","Earl","Jimmy","Antonio","Danny",
				"Bryan","Tony","Luis","Mike","Stanley","Leonard","Nathan","Dale","Manuel","Rodney","Curtis","Norman","Allen",
				"Marvin","Vincent","Glenn","Jeffery","Travis","Jeff","Chad","Jacob","Lee","Melvin","Alfred","Kyle","Francis",
				"Bradley","Jesus","Herbert","Frederick","Ray","Joel","Edwin","Don","Eddie","Ricky","Troy","Randall","Barry",
				"Alexander","Bernard","Mario","Leroy","Francisco","Marcus","Micheal","Theodore","Clifford","Miguel","Oscar",
				"Jay","Jim","Tom","Calvin","Alex","Jon","Ronnie","Bill","Lloyd","Tommy","Leon","Derek","Warren","Darrell",
				"Jerome","Floyd","Leo","Alvin","Tim","Wesley","Gordon","Dean","Greg","Jorge","Dustin","Pedro","Derrick","Dan",
				"Lewis","Zachary","Corey","Herman","Maurice","Vernon","Roberto","Clyde","Glen","Hector","Shane","Ricardo","Sam",
				"Rick","Lester","Brent","Ramon","Charlie","Tyler","Gilbert","Gene","Marc","Reginald","Ruben","Brett","Angel",
				"Nathaniel","Rafael","Leslie","Edgar","Milton","Raul","Ben","Chester","Cecil","Duane","Franklin","Andre","Elmer",
				"Brad","Gabriel","Ron","Mitchell","Roland","Arnold","Harvey","Jared","Adrian","Karl","Cory","Claude","Erik",
				"Darryl","Jamie","Neil","Jessie","Christian","Javier","Fernando","Clinton","Ted","Mathew","Tyrone","Darren",
				"Lonnie","Lance","Cody","Julio","Kelly","Kurt","Allan","Nelson","Guy","Clayton","Hugh","Max","Dwayne","Dwight",
				"Mary","Patricia","Linda","Barbara","Elizabeth","Jennifer","Maria","Susan","Margaret","Dorothy",
				"Lisa","Nancy","Karen","Betty","Helen","Sandra","Donna","Carol","Ruth","Sharon","Michelle","Laura","Sarah",
				"Kimberly","Deborah","Jessica","Shirley","Cynthia","Angela","Melissa","Brenda","Amy","Anna","Rebecca","Virginia",
				"Kathleen","Pamela","Martha","Debra","Amanda","Stephanie","Carolyn","Christine","Marie","Janet","Catherine","Frances",
				"Ann","Joyce","Diane","Alice","Julie","Heather","Teresa","Doris","Gloria","Evelyn","Jean","Cheryl","Mildred","Katherine",
				"Joan","Ashley","Judith","Rose","Janice","Kelly","Nicole","Judy","Christina","Kathy","Theresa","Beverly","Denise","Tammy",
				"Irene","Jane","Lori","Rachel","Marilyn","Andrea","Kathryn","Louise","Sara","Anne","Jacqueline","Wanda","Bonnie","Julia",
				"Ruby","Lois","Tina","Phyllis","Norma","Paula","Diana","Annie","Lillian","Emily","Robin","Peggy","Crystal","Gladys","Rita",
				"Dawn","Connie","Florence","Tracy","Edna","Tiffany","Carmen","Rosa","Cindy","Grace","Wendy","Victoria","Edith","Kim",
				"Sherry","Sylvia","Josephine","Thelma","Shannon","Sheila","Ethel","Ellen","Elaine","Marjorie","Carrie","Charlotte","Monica",
				"Esther","Pauline","Emma","Juanita","Anita","Rhonda","Hazel","Amber","Eva","Debbie","April","Leslie","Clara","Lucille",
				"Jamie","Joanne","Eleanor","Valerie","Danielle","Megan","Alicia","Suzanne","Michele","Gail","Bertha","Darlene","Veronica",
				"Jill","Erin","Geraldine","Lauren","Cathy","Joann","Lorraine","Lynn","Sally","Regina","Erica","Beatrice","Dolores","Bernice",
				"Audrey","Yvonne","Annette","June","Samantha","Marion","Dana","Stacy","Ana","Renee","Ida","Vivian","Roberta","Holly",
				"Brittany","Melanie","Loretta","Yolanda","Jeanette","Laurie","Katie","Kristen","Vanessa","Alma","Sue","Elsie","Beth",
				"Jeanne","Vicki","Carla","Tara","Rosemary","Eileen","Terri","Gertrude","Lucy","Tonya","Ella","Stacey","Wilma","Gina",
				"Kristin","Jessie","Natalie","Agnes","Vera","Willie","Charlene","Bessie","Delores","Melinda","Pearl","Arlene","Maureen",
				"Colleen","Allison","Tamara","Joy","Georgia","Constance","Lillie","Claudia","Jackie","Marcia","Tanya","Nellie","Minnie",
				"Marlene","Heidi","Glenda","Lydia","Viola","Courtney","Marian","Stella","Caroline","Dora","Jo","Vickie","Mattie","Terry",
				"Maxine","Irma","Mabel","Marsha","Myrtle","Lena","Christy","Deanna","Patsy","Hilda","Gwendolyn","Jennie","Nora","Margie",
				"Nina","Cassandra","Leah","Penny","Kay","Priscilla","Naomi","Carole","Brandy","Olga","Billie","Dianne","Tracey","Leona"};

		Random r = new Random();
		String name = names[r.nextInt(names.length)];
		String surname = surnames[r.nextInt(surnames.length)];
		return name + " " + surname;
	}
}
