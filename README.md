# Sample AEM project template

This is a project template for AEM-based applications. It is intended as a best-practice set of examples as well as a potential starting point to develop your own functionality.

## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi services, listeners or schedulers, as well as component-related Java code such as servlets or request filters.
* it.tests: Java based integration tests
* ui.apps: contains the /apps (and /etc) parts of the project, ie JS&CSS clientlibs, components, and templates
* ui.content: contains sample content using the components from the ui.apps
* ui.config: contains runmode specific OSGi configs for the project
* ui.frontend: an optional dedicated front-end build mechanism (Angular, React or general Webpack project)
* ui.tests: Selenium based UI tests
* all: a single content package that embeds all of the compiled modules (bundles and content packages) including any vendor dependencies
* analyse: this module runs analysis on the project which provides additional validation for deploying into AEMaaCS

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

To build all the modules and deploy the `all` package to a local instance of AEM, run in the project root directory the following command:

    mvn clean install -PautoInstallSinglePackage

Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallSinglePackagePublish

Or alternatively

    mvn clean install -PautoInstallSinglePackage -Daem.port=4503

Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

Or to deploy only a single content package, run in the sub-module directory (i.e `ui.apps`)

    mvn clean install -PautoInstallPackage

## Testing

There are three levels of testing contained in the project:

### Unit tests

This show-cases classic unit testing of the code contained in the bundle. To
test, execute:

    mvn clean test

### Integration tests

This allows running integration tests that exercise the capabilities of AEM via
HTTP calls to its API. To run the integration tests, run:

    mvn clean verify -Plocal

Test classes must be saved in the `src/main/java` directory (or any of its
subdirectories), and must be contained in files matching the pattern `*IT.java`.

The configuration provides sensible defaults for a typical local installation of
AEM. If you want to point the integration tests to different AEM author and
publish instances, you can use the following system properties via Maven's `-D`
flag.

| Property | Description | Default value |
| --- | --- | --- |
| `it.author.url` | URL of the author instance | `http://localhost:4502` |
| `it.author.user` | Admin user for the author instance | `admin` |
| `it.author.password` | Password of the admin user for the author instance | `admin` |
| `it.publish.url` | URL of the publish instance | `http://localhost:4503` |
| `it.publish.user` | Admin user for the publish instance | `admin` |
| `it.publish.password` | Password of the admin user for the publish instance | `admin` |

The integration tests in this archetype use the [AEM Testing
Clients](https://github.com/adobe/aem-testing-clients) and showcase some
recommended [best
practices](https://github.com/adobe/aem-testing-clients/wiki/Best-practices) to
be put in use when writing integration tests for AEM.

## Static Analysis

The `analyse` module performs static analysis on the project for deploying into AEMaaCS. It is automatically
run when executing

    mvn clean install

from the project root directory. Additional information about this analysis and how to further configure it
can be found here https://github.com/adobe/aemanalyser-maven-plugin

### UI tests

They will test the UI layer of your AEM application using Selenium technology. 

To run them locally:

    mvn clean verify -Pui-tests-local-execution

This default command requires:
* an AEM author instance available at http://localhost:4502 (with the whole project built and deployed on it, see `How to build` section above)
* Chrome browser installed at default location

Check README file in `ui.tests` module for more details.

## ClientLibs

The frontend module is made available using an [AEM ClientLib](https://helpx.adobe.com/experience-manager/6-5/sites/developing/using/clientlibs.html). When executing the NPM build script, the app is built and the [`aem-clientlib-generator`](https://github.com/wcm-io-frontend/aem-clientlib-generator) package takes the resulting build output and transforms it into such a ClientLib.

A ClientLib will consist of the following files and directories:

- `css/`: CSS files which can be requested in the HTML
- `css.txt` (tells AEM the order and names of files in `css/` so they can be merged)
- `js/`: JavaScript files which can be requested in the HTML
- `js.txt` (tells AEM the order and names of files in `js/` so they can be merged
- `resources/`: Source maps, non-entrypoint code chunks (resulting from code splitting), static assets (e.g. icons), etc.

## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html
1. create an OSGi bundle which has OSGi service which reads JSON data using HTTP GET request from the URL

## Notes
--------JAVA OOPs Concepts---------
1. Explain briefly OOPs Concept? - Object
   Class,Inheritance,Polymorphism,Abstraction,Encapsulation

2. Why static keyword is used? - static keyword is used for memory management mainly. We can apply static keyword with variables, methods
   The static variable can be used to refer to the common property of all objects (eg: static String college="IELTS")

3. Can a class extend multiple class? - Multiple inheritance is not allowed.

------------------Collections-----------
1. Basic interfaces of the Java Collection Framework? - Set,List,Collection

2. ArrayList vs LinkedList?- ArrayList is preferred when storing and accessing is required while Linked list is prefered when manipulation is required.

3. Why do we use Map in Java?

----------------Exception-------
1. How the exceptions are handled in java?using try, catch and finally blocks
2. what are checked and unchecked exceptions in java? Checked exceptions are the exceptions which are known to compiler.These exceptions are checked at compile time only. ClassNotFoundException, SQLException, IOException

                                                      Unchecked exceptions are those exceptions which are not at all known to compiler. These exceptions occur only at run time.NullPointerException, ArrayIndexOutOfBoundsException, NumberFormatException

----------------Stream API------
1. Stream API is used to process collections of objects.
2. Using stream API , get the list starting with "S"
   List names = Arrays.asList("Reflection","Collection","Stream");
   List result = names.stream().filter(s->s.startsWith("S")).collect(Collectors.toList());												  
   3.Lambda expressions are similar to methods, but they do not need a name and they can be implemented right in the body of a method.
   4.Serialization is a mechanism of converting the state of an object into a byte stream.

---------HTML/CSS/JavaScript---------
1. difference between  let and const.
2. forEach vs Map?
3. worked on javascript library like React or framework like Angular.
   4.jQuery is a popular library for simiplifying interactions between DOM and JavaScript

--------REST API------------
Worked on REST API?

---------AEM--------
1.What is maven archetype/How do u create project template in AEM?- The AEM Project Archetype is a Maven template that creates a minimal, best-practices-based Adobe Experience Manager.
2.AEM architecture stack of AEM.
3.Have you worked on sling models? efaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
@Model(adaptables = SlingHttpServletRequest.class,
adapters = AccordionCmp.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

4.
@Model(adaptables = Resource.class)
public class TestModel {

    // Injects the child of the resource using ChildResource annotation
    @ChildResource(name="content")
    Resource child;

    public String getChildPath() {
      return  child.getPath();
    }
}
ChildResource : This injector is adaptable to resource and is used to get the specific child of a resource.

5. Script Variable: This Injector is used to get the currentPage, PageManager, Design, PageProperties etc.
   This injector is adaptable to request.This injector is adaptable to SlingHttpServletRequest.
   @Model(adaptables = SlingHttpServletRequest.class)
   public class TestModel {

// Injects currentPage using ScriptVariable annotation
@ScriptVariable(name="currentPage")
Page page;

    public String getPagePath() {
        return  page.getPath();
    }
}

6. Template Types are effectively a template for a template.

7. Where is template type stored:
   /conf/wknd/settings/wcm/template-types/empty-page/structure/jcr:content
   8.responsive layout for your pages??
   AEM allows you to have a responsive layout for your pages by using the Layout Container component.
   This provides a paragraph system that allows you to position components within a responsive grid.
   This grid can rearrange the layout according to the device/window size and format.

9.Style System?
Template authors not only need the ability to configure how components function for the content authors,
but also to configure a number of alternative visual variations of a component.


4.Diff btw Felix SCR annotations and OSGi R6 annotations?
5. How do u create configurable service in AEM using DS annotations.
6. How do u create servlet using DS?
   @Component(
   service = Servlet.class,
   property = {
   "sling.servlet.extensions=html",
   "sling.servlet.selectors=foo",
   "sling.servlet.paths=/bin/foo",
   "sling.servlet.paths=/bin/bar",
   "sling.servlet.methods=get",
   "sling.servlet.resourceTypes=nt:file",
   "sling.servlet.resourceTypes=project/components/component"
   }
   )
7. Worked on Event Listener- We can achieve event handling at the JCR level by implementing the EventListener interface in a class.Override onEvent method.
   @Component(service = EventListener.class, immediate = true)
   public class CustomEventListener implements EventListener {...}
8. How do i get the resource progammatically in AEM.
9.  Sling API------Resource
    JCR API----Node
    adaptTo = Sling comes with adapters mechanism. One object can be adopted to other.
    ValueMap properties = resource.adaptTo(ValueMap.class);
    String property = properties.get("myProperty", String.class);

Authoring skills
Overlay components
Customer components
Editable templates
crxde lite
Clientlibs
Sightly
Workflows real life use cases
Replication Agents, reverse replication real life use cases
Configuration console & crxde lite for configurations
Custom looging
MSM

Should have done Dispatcher configurations or modified
Adobe marketing cloud integration - Adobe I/O, AEM & Java code
At least one AEM upgrade project or Service Pack Updgrade
Reports and dashboards
Knowdlege on version differences (preferrably 6.5/6.4/6.3 & cloud services)
Basic Web Technology concepts: Session, cookies, http, https, smtp etc
Knowledge on Security and Performance considerations

A developer needs to change the label “Sites” in the navigation of AEM author to “Websites”
Modify the node /libs/cq/core/content/nav/sites by updating the icr:title property value.

A developer creates an AEM editable template that includes a Layout Container.
When the developer creates a page using this template, the Layout Container placeholder does NOT appear.
The Layout Container has NOT been unlocked.

You want to restrict the size of a custom log file and enable log file rotation. Which Apache
Sling configuration has to be changed in the AEM Web Console?
Apache Sling Logging Logger Configuration.

The model must check if the configured value of the jcr:title property for the component matches the name of the current page. If the jcr:title property of the component has NOT been configured, then isMatchingTitle() must
return false

6. What are the requests not cached by the dispatcher?
   Request that does not return http code 200
   Requests with suffixes
   Requests with request parameter(i.e ?)
>Programmatically: send an http header response.setHeader("Dispatcher", "no-cache")

18. How to prevent one particular component to be cached, while you are caching the whole page into dispatcher?
    We have to configure the component(which we don't want to be cached) into Sling Dynamic Include (SDI).

The /renders property defines the URL to which Dispatcher sends requests to render a document.
/renders {
/0001 {
/hostname "host-name-here"
/port "4502"
/ipv4 "1"
/always-resolve "1"
}
}

Permission-sensitive caching enables you to cache secured pages.
Dispatcher checks user’s access permissions for a page before delivering the cached page.
Dispatcher includes the AuthChecker module that implements permission-sensitive caching.
{
# request is sent to this URL with '?uri=<page>' appended
/url "/bin/permissioncheck"

# only the requested pages matching the filter section below are checked,
# all other pages get delivered unchecked
/filter
{
/0000
{
/glob "*"
/type "deny"
}
/0001
{
/glob "/content/secure/*.html"
/type "allow"
}
}
# any header line returned from the auth_checker's HEAD request matching
# the section below will be returned as well
/headers
{
/0000
{
/glob "*"
/type "deny"
}
/0001
{
/glob "Set-Cookie:*"
/type "allow"
}
}

A developer is creating a custom component on the page /latestBlogs.html that needs to list all the titles of the blogs pages under /content/blogs.
How does this component get the list of child pages?
Use PageManager.getPage(“/content/blogs”) of the static PageManager class to instantiate a Page object and then iterate through the child pages and print the title for each


A developer has a component located under the path /apps. This component has a Client Library which is directly loaded onto a page.
The publish instance loads the page correctly. The dispatcher also loads the page but the Client Library is missing.
How should the developer resolve this issue, while taking security into consideration?
Add the property allowProxy with a boolean value true

One of the most important meta files of a vault checkout or a content package is the filter.xml which is present in the META-INF/vault directory.
The filter.xml consists of a set of filter elements, each with a mandatory root attribute and an optional list of include and exclude child elements.
MERGE: Existing content is not modified, i.e. only new content is added and none is deleted or modified.
UPDATE: Existing content is updated, new content is added and none is deleted.
REPLACE: This is the normal behavior. Existing content is replaced completely by the imported content, i.e. is overridden or deleted accordingly.


How to run some configuration specific to AEM instance.
create node -> apps/project/config.author.dev
create child node -> type will be sling:OSGIConfig and name should be same as PID

Run  component on specific Environment eg. publish
Create component and annotate with policy =ConfigurationPolicy.REQUIRE
create node under config.publish-> com.sumit.MyComponentnz(slingOsgiConfig)

BundleA for author and Bundle B for publish.
install.author -> BundleA.jar
install.publish-> BundleB.jar


 <span data-sly-text="${properties.title}"> Some text</span>

 <span data-sly-attribute.title="${properties.title}" title="Lorem Ipsum"> Hello World</span>

 <h1 data-sly-element="p">Lorem Ipsum</h1>

data-sly-list vs data-sly-repeat

 <ul data-sly-repeat="${currentPage.listChildren}">

 </ul>

1. Assuming we have resource /x/y, resolve below URI:
   /x/y.s1.s2.html/a/b.s.txt		
   Resource - /x/y
   Selector - s1.s2
   Extension- html
   Suffix - /a/b.s.txt
   Resource Found - Yes

2. Using querybuilder- Search for pages tagged with a certain tag.
   type=cq:Page
   tagid=marketing:interest/product
   tagid.property=jcr:content/cq:tags

3. How to use QueryBuilder API

a. create query desc Hashmap
b. Query query = builder.createQuery(PredicateGroup.create(map), session);
c. SearchResult result = query.getResult();

4. How to include JSP in your HTL file.
   <sly data-sly-include="template.jsp"/>

5. Can you create a component without cq:dialog?
   Without cq:dialog the component cannot be added to page.


Problem Statement: In the Touch UI Dialogs ,If there is a need to put some validation on fields or we just want to add some CSS,then use “cq.authoring.dialog” clientLibrary.This clientlibs is automatically loaded when the dialog opens.So, No need to make an entry of it on any page.

This created a performance issue because if the clientLibrary is so big and it is getting loaded in all dialogs regardless of dialog needs it or not.

Solution:

Using extraClientlibs Property: In the cq:dialog node of a dialog, Add these properties:
1. Name:extraClientlibs
Type: String[]
Value: ClientLib_Category_Name
2. Name: mode
Type:String
Value:edit”

6. ----------------Sling Model Exporter--------------------

   @Exporter(name="exporter",extensions="type",selector="selector")		  
   name-> of the exporter
   extensions-> which format the exporter will return.
   selector->
   Eg: Jackson is OOTB Exporter provided by AEM:
   @Exporter(name="jackson",extensions="json",selector="geek")
   model - is default selector
   http://localhost:4502/content/sumit/us/en/magazine/prayagraj-skateboards/jcr:content/root/container/container/aemgeek.geek.json

6.1 It exports all getter method in JSON format.
6.2 To export non getter method, use @JsonProperty.
@JsonProperty(value="scholName")
public String school()
{
return "SMC";
}
6.3 To change the name of Json Key, we use @JsonProperty in getter method:

@JsonProperty(value="teacher")
public boolean getProfessor() {
return professor;
}
6.4 If any value is null, we use @JsonIgnore to not produce that json key.
6.5 To wrap all json object in some root json key. We use options attribute in @Exporter
and annotate the class with @JsonRootName("rootKey")
@Exporter(name="jackson",extensions="json",selector="geek",options={
@ExporterOption(name="SerializationFeature.WRAP_ROOT_VALUE",value="true")})
@JsonRootName("rootKey")
6.6 To write custom Sling Model Exporter- i.e XML exporter(We will use JAXB API)
We need to create AEM Service implementing ModelExporter.class and override 3 methods:
1. boolean isSupported
2. String getName
3. export
6.7 We can have multiple exporters with @Exporters option- Refer AEMGeekImpl.java

7.------------Service User-------------

7.1 Create System User using /crx/explorer/index.jsp and give permissions in user admin for this user.
Or Create a sysem user  and add permissions using ACS Commons Config -> Ensure Service User
7.2 Map this user in AEM config -> Apache Sling Service User Mapper Service Amendment
{bundleName}:{servicename}={serviceuser}
aem-sumit.core:sumitservice=sumit-system-user
7.3 Write Util class to get resource resolver. Refer ResourceResolverUtil.java

8.------------Logging--------------------

8.1 We have 5 log levels in order as:
Trace
Debug
Info
Warn
Error
8.2 Custom log files can be created to log specific package or class using config -> Apache Sling Logging Logger Configuration.
8.3 AEM provides console to view/add logs in browser -> http://localhost:4502/system/console/slinglog
8.4 AEM provides config to configure log rotation based on file size and time -> Apache Sling Logging Writer Configuration.

9------------OSGI Service--------------------

9.1 Service will always implement interface
Mandatory annotation -> @Component(service=SomeInterface.class)
Optional annotation -> @Activate , @Deactivate

9.2 When we have two implementation of same interface, we can define ranking of osgi service.

     MultiService(Interface) -> getName()
	 MultiServiceImplA() -> Implementation of getName()
	 MultiServiceImplB() -> Implementation of getName()
	 
	 When we use this service , by default priority will be given to service with lower Service ID.
     Service ID can be found in AEM console when we click core bundle.

9.3 But we can give ranking to each implementation to call them separately by annotating service with @ServiceRanking(1001).
eg:
@Component(service= MultiService,name=ServiceA)
@Ranking(1001)
MultiServiceImplA()

9.4 To call specific service in sling Model, we use filter attribute. Below calls MultiServiceImplB.
@OsgiService(filter="(component.name=ServiceB)")
MultiService service;

9.5 To call specific service in Component, we use target attribute.
@Reference(target="(component.name=ServiceB)")

10-----------OSGI COnfig using r7 annotation--------

10.1 Create @interface GeeksOSGiConfig and annotate with @ObjectClassDefinition(name="Modular OSGi Configuration",description = "Modular OSGi Configuration demo.")
This interface will map all values from configMgr using @AttributeDefinition
10.2 Create OSGI Service and annotate with @Designate(ocd=GeeksOSGiConfig.class) and inject above config in @Activate Method.

11-----------OSGI Factory Configuration-------------

When we need different instance of same OSGI configuration, we declare OSGI config as factory configuration.
This is achieved by adding attribute factory=true in @Designate .

11.1 Inside Impl we create new List:
private List<OSGiFactoryConfig> factoryConfigsList; //OSGiFactoryConfig -> is our interface with all config mapping
11.2 We add bind and unbind method as:

      @Reference(service = OSGiFactoryConfig.class,cardinality = ReferenceCardinality.MULTIPLE,policy = ReferencePolicy.DYNAMIC)
      public void bindOSGIFactoryConfig( final OSGiFactoryConfig osGiFactoryConfig) {
    
       factoryConfigsList.add(osGiFactoryConfig);
      }

      public void unbindOSGIFactoryConfig( final OSGiFactoryConfig osGiFactoryConfig) {
       factoryConfigsList.remove(osGiFactoryConfig);
      }
11.3 ReferenceCardinality.MULTIPLE -> This method will listen to service everytime.
ReferencePolicy.DYNAMIC -> It will find unbind method with same name automatically.

12----------------Sling Servlet---------------

Servlet can be registered using OSGi DS 1.4(R7) which needs bnd-maven-plugin 4.0+ -> @SlingServletResourceTypes ,@SlingServletPaths
Servlet can be registered using OSGi DS 1.2(RS) which needs mvn-bundle-plugin 3.0+ -> @Component(service=Servlet.class)
Servlets can be registered using resourceType or path.

12.1 Using resourceType: Servlet is bound to resource and can be access controlled using JCR repository ACL.
Servlets use methods,selector and extensions.

13---------------Sling Schedular-------------

Schedular can be registered by implementing either:
13.1 Runnable Interface -> implement run method
13.2 Job Interface -> implement execute(JobContext ctx)
13.3 Status of scheduler can be checked at http://localhost:4502/system/console/status-slingscheduler

We need to implement JobInterface if we want to run scheduler in different time intervals with different parameters.

14-------------QueryBuilder------------------

Two search API's(Repository Search) used in AEM are - QueryBuilder API and JCR SQL2
AEM Console to execute query -> http://localhost:4502/libs/cq/search/content/querydebug.html

14.1 Whatever we want to search we provide in predicate called type and value should be the primaryTpe.
Bydefault AEM will restrict result to 10 results but we can use p.limit = -1 to show all results.

14.2 Search all pages under given path
type = cq:Page
path = /content/sumit/
orderby = @jcr:content/cq:lastModified
orderby.sort = desc
p.limit= -1

14.3 To search a property we use property predicate.
Search all pages with given template
type = cq:Page
path = /content/sumit/
property = jcr:content/cq:template
property.value = /conf/sumit/settings/wcm/templates/page-content

14.4 To search multiple values with given property:
By default its an OR opertaion when we search single property. It will find either title is Magazine or title is FAQ's
Search all pages with jcr title Magazine or FAQs
type = cq:Page
path = /content/sumit/
property = jcr:content/jcr:title
property.1_value = Magazine
property.2_value = FAQs

	  To search with AND operation on same property -> property.and = true

14.5 To search multiple property:
By default its an AND operation when searching multiple property.
type = cq:Page
path = /content/sumit/
1_property = jcr:content/jcr:title
1_property.value = Magazine
2_property = jcr:content/cq:template
2_property.value = /conf/sumit/settings/wcm/templates/page-content

	  In order to apply OR operation while multiple property search operation , we use predicate group.
	  type = cq:Page
      path = /content/sumit/
	  group.p.or = true
      group.1_property = jcr:content/jcr:title
      group.1_property.value = Magazine
      group.2_property = jcr:content/cq:template
	  group.2_property.value = /conf/sumit/settings/wcm/templates/page-content

	  When we search with above query, you can see the default property returned by JCR in JSON QueryBuilder Link.
	  eg: For above query we see result as:
	  {
        path: "/content/sumit/us/en/magazine",
        excerpt: "",
        name: "magazine",
        title: "Magazine",
        lastModified: "2022-05-23 15:47:00",
        created: "2022-03-06 21:36:18"
      }
	  We can alter this behavior to show properties according to our requirement.
14.6 p.hits = full -> will give full property name. Eg: above result will look like->
{
"jcr:path": "/content/sumit/us/en/magazine",
"jcr:primaryType": "cq:Page",
"jcr:createdBy": "admin",
"jcr:created": "Sun Mar 06 2022 21:36:18 GMT+0530"
}

14.7 To get selective properties:
p.hits = selective
p.properties = jcr:content/sling:resourceType

	 eg: above will become as
	 {
      "jcr:content": {
           "sling:resourceType": "sumit/components/page"
                    }
     }

14.8 To search property tags, we can make use of predicate called tagid.
type = cq:Page
path = /content/sumit
tagid.property = jcr:content/cq:tags
tagid = we-retail:activity/swimming

14.9 We make use of predicate daterange to search for dates

      type = cq:Page
      path = /content/sumit
      daterange.property = jcr:content/cq:lastModified
      daterange.lowerBound = 2021-12-11
      daterange.upperBound = 2022-12-11 

14.10 If a query result exceeds more eg: 200+ ,
p.limit = 20 -> will always give 20 result
We can combine p.limit with another predicate called p.offset to fetch next values eg:
p.offset = (n-1) -> we pass value of n from frontend i.e 1,2 etc and based on that we see next results.
p.limit = 20

14.11 Whenever we search, then query always return all result and then based on p.limit it shows the data.
However we can restrict the behavior to searh all data and instruct to search top 10 or 20 etc results using another
predicate called p.guessTotal

       type = dam:Asset
       path = /content/dam/we-retail
       p.offset = 0
       p.limit = 20
       p.guessTotal = true -> will search only 20 results

Note - we can also assign p.guessTotal = 100, to search in top 100 results only.



15. ----Composite Components in AEM using "cq:template"------

15.1  Create a node (nt:unstructured) called cq:template under your component.
15.2  Create another node below cq:template, eg: container and sling:resourceType -> foundation/components/parsys
15.3  Create node that you want to be dropped by default eg : text and point it to respective resourceType.
15.4  Make sure the sightly of your component includes ->
<div data-sly-resource="${'container' @ resourceType='foundation/components/parsys'}" data-sly-unwrap></div>
Here name "container" should match with the node name created under cq:template node.

16. ----Event Handling---------

AEM provides 4 ways to handle events using below API's:

16.1 JCR API's ->  implement EventListener. Event handling done at JCR/Repository level and registered via ObservationManager (refer JCRHandler.java)

16.2 OSGi API's ->  implement EventHandler (refer OSGiEventHandler.java)
a. are registered with Framework service registry(i. like OSGi service) and are notified with an Event Object.
b. can inspect the received event object to determine its topic and properties.
c. event objects must be registered with a service property EventConstants.EVENT_TOPIC whose values are list of topics
for which the event handler listen to. 	  
d. can also be registered with EventConstants.EVENT_FILTER service property to further filte the events.


16.3 Sling API's -> implement ResourceChangeListener
a. are registered with Framework service registry(i. like OSGi service) and are notified with an ResourceChange Object.
b. can inspect the received ResourceChange objects to determine the type of change,location and other properties.
c. ResourceChange must be registered with a service property PATHS whose value is list of resource paths .
d. must be registered with service property CHANGES whose value is list of event type.

16.4 Sling Job Manager/Consumer -> JobManager and JobConsumer
a. gives guruantee of processing atleast once.
b. JobManager allows to create new jobs , search for jobs, and get staistics about the current state.
c. JobConsumer consumes a Job. It registers with the PROPERTY_TOPICS service registry property.
d. Steps to create ->
1. Create a Event Handler(using Sling/OSGi API) -> GeeksJobCreater.java
2. Inside the Handler, we will create a Custom Job using Job Manager i.e Job job=jobManager.addJob("geeks/job",jobProperties);
3. We create JobConsumer Service to consume this Job.(GeeksJobConsumer.java) This Job consumer returns one of 4 status name:
OK -> Process Finished Successfully
FAILED -> Proceesing Failed but might be retried(depending on the limit of retries set)
CANCEL -> Processing failed permanently and will not be retried.
ASYNC -> Processin Will Be Done Asynchronously.

NOTE - 1. If a Job is failed , then AEM stores the data under /var/eventing/jobs/cancelled with all relevant information.
2. AEM Sling Event Console http://localhost:4502/system/console/slingevent contains info like queue,retries etc.
3. We can edit retries , delay etc in config called - Apache Sling Job Default Queue



17.    ---Context Aware Configuration---

17.1 UseCase -> If we want the different configuration for different page hierarchy eg: us,in,de then we can make use of factory OSGi
configuration but if we want different config for subnodes as well under them eg: under us -> en and es and so on...In such cases
factory config will become complex to manage.
To overcome this, we make use of Context Aware Configuration.
17.2 CAC are stored under /conf/<project>. eg: /conf/sumit/us , /conf/sumit/in
17.3 We add property sling:configRef to page hierarchy in /content. The value of this property is equal to specific config that we want to load from /conf.
17.4 Context Aware configuration resolve from child to parent. For example
/conf/aemgeeks/us/en/sling:configs/GeeksCAConfig
/conf/aemgeeks/us/sling:configs/GeeksCAConfig
/conf/aemgeeks/sling:configs/GeeksCAConfig
If CA Config does not exist in /conf/aemgeeks/us/en/ then it will look at parent /conf/aemgeeks/us/
it keep looking to parents until it find configuration. Keep in mind, you have added sling:configRef property in content.
17.5 Steps to create context aware.
a. Create OSGI Config and Sling Model consuming the config -> CAConfigImpl.java
b. Under /conf/sumit/sling:configs/ create a node nt:unstructured -> com.aem.sumit.core.services.contextaware.GeeksCAConfig. Add the properties
that were defined in OSGI Service.
c. Under /content/sumit/jcr:content -> Add a property called sling:configRef -> assign the values as /conf/sumit. This will look for sling:configs
in /conf/sumit and assign the value to our component.

18.   --------Workflow----------

18.1 To ceate Workflow,Workflow Model needs to be created which contains series of steps.
Worflow model is stored in 2 places:
1. /conf/global/settings/workflow/models/geeks-model -> Model Design
2. /var/workflow/models/geeks-model -> Runtime Model (used when workflow is executing)

18.2 Different Steps:
1. Container Step -> When you want to execute another workflow(child workflow)
2. Participant Step -> When you want human interaction i.e assign some task.
3. Decision Step -> When you need branching (AND / OR)
4. Process Step -> Own custom process.

18.3 Common ways to execute workflow:
1.Launcher -> When you want to automate the trigger of the workflow eg: perform some task when page is created.
2. API/Code
3. Manually.



18.4 Launcher can be created with console - http://localhost:4502/libs/cq/workflow/admin/console/content/launchers.html.
If we have access to WorkflowModel and Payload , then we can trigger Workflow from backend (SlingModel,Servlet,Scheduler,Services)
Below are the steps to trigger:
1. Get WorkflowSession ->          WorkFlowSession session = resourceResolver.adaptTo(WorkflowSession.class)
2. Get WorkflowModel   ->          WorkflowModel model  = session.getModel("/var/...")
3. Get WorkFlowData(i.e payload)   WorkflowData data = session.newWorkflowData("JCR_PATH",payload);
4. Start workflow      ->          session.startWorkflow(model , data)

	  We can also pass metadata.         Map<String,Object> metaData = new HashMap();
	                                     session.startWorkflow(model , data, metaData);
										 
		http://localhost:4502/libs/cq/workflow/admin/console/content/models.html -> 
		Page Version For All Sumit Pages 
		1. This workflow creates a page version and is triggered via launcher which listens for cq:Page modified under /content/sumit/
        2. Launcher can be searched with globbing pattern /content/sumit/us	in Launchers Console.
        Page Version
        1. This workflow makes use of custom workflow process created below(i.e GeeksWorkflowProcess.java)	
        2. This workflow is triggered from servlet - SimpleWorkflow.java.
           http://localhost:4502/bin/executeworkflow?page=/content/sumit/us/en/adventures
           When we hit the servlet, the version will be created for page passed as query parameter and two properties(approver1 and approver2) will be added in jcr:content .		   

18.5 Custom Workflow Process - GeeksWorkflowProcess.java (This custom process step will add two properties(approver1 and approver2) in the page that is passed as Payload and create a version)
1. This will be availabele inside dropdown of Process Step.
2. implements WorkflowProcess and overrides execute(workitem,workflowsession,metadatamap)
3. property -> process.label is important as it will be selected in process step.
4. We can get dialog properties by MetaDataMap using key ("PROCESS_ARGS")

	  NOTE -> Custom Workflow Process and Custom Workflow Step are two different things.
	          Custom Workflow Process -> is a backend module which can be selected when we use Process Step .
			                             It doesn't have its dialog and have to use dialog of Process Step.
              Custom Workflow Step -> To write custom workflow step, we need a component. This component will not have rendering script and backend logic.
                                      It will have dialog and editConfig node. When we link Custom Workflow Process (eg:GeeksWorkflowProcess ) to this
            						  component,then this setup is referred as Custom Workflow Step.
                                      This will be now available as a new step in workflow model, which we can drag and drop.									  

18.6 Complete flow for creating Custom Workflow Step.
1. Created custom workflow process -> GeeksWorkflowStep.java
2. Created component -> geeksworkflowstep. It has cq:dialog and cq:editConfig.
Under cq:editConfig -> cq:formParameters , important property is PROCESS whose value is path of our
Workflow Process created in Step1.
i.e  PROCESS="com.aem.sumit.core.workflow.GeeksWorkflowStep"
3. For the first time , this new Workflow Step will not be available to drag and drop in Workflow Model.
We need to copy the 'default' node from /libs/settings/wcm/designs/default	  to /apps/settings/wcm/designs/default
4. Under /apps/settings/wcm/designs/default/jcr:content/model/flow , we added our component group .
5. This new Custom Workflow Step can now be added by drag and drop and values can be configured in dialog.

     NOTE - Whatever property that we define in cq:dialog, the name should be prepended with /metaData
            eg: name -> ./metaData/BRAND	 


18.7 Refer Workflow Model - Geek Participant Step -> to understand Partipant Step.

18.8 Workflow Stages -> Whenever an approve approves/rejects a task assigned , he is not aware of the history of the workflow i.e how far it has reached.
With the help of workflow stages, we divide the entire workflow into logical sections and each section contains the info.
Refer workflow-stage.png for pictorial view.
1. To create stages/sections, edit the model and select Stages Tab and add the stages you want(eg: Create, Review, Approve)
2. Open the Workflow Step, and you will find the new drop-down where you can assign the stage.
3. Once stages are assigned. Approver can go to inbox and click on open -> Workflow Info .

18.9 Refer Worflow Model - Geek Publish Workflow -> approve/reject the page activation.	  

--------------------------------------------Dispatcher Setup----------------------------------------------
1. Download httpd-2.2.2-win32-x86-no_ssl (apache web server) and dispatcher module dispatcher-apache2.2-windows-x86

2. Double click ssl file and install and change the directory to C:\apache2\

3. Copy disp_apache2.2.dll to C:\apache2\modules.

4. In apache 2.2, starting point is httpd.conf located at C:\apache2\conf.

5. Open httpd.conf and add the module we added:
   LoadModule dispatcher_module modules/disp_apache2.2.dll

6. Copy IF block from httpd.conf.disp2 and paste in httpd.conf  . httpd.conf.disp2 file is located in dispathcer-apache2.2
   <IfModule disp_apache2.c> ....</IfModule disp_apache2.c>

7. The IF module tells about the configuration files of dispatcher.

8. Again copy the <Directory /> block to httpd.conf

9.The <Directory /> block has "SetHandler dispatcher-handler" which tells that all processing will be done by
our module that we loaded i.e disp_apache2.2.dll

10. Copy dipatcher.any to C:\apache2\conf
    Note: In httpd.conf, ensure that "ServerRoot "C:/apache2" always points to apache installation directory.

11. Restart the apache server. If everything is fine server will restart and log with name "dispatcher.log" will be
    generated at C:\apache2\logs

12. Change the cache root path in dispatcher.any under /cache farm.
    /docroot "C:/apache2/htdocs"


##AEM + SOLR Integration
a)...Below are the use cases for different scenarios.
 1. Servlet - Index,Delete and Search Page Data to Solr.
 2. Scheduler - Index AEM Page to Solr at scheduled interval for different sites.
 3. ReplicationEventListener - Index AEM Page on publish and Un-Publish.

 Classes Included are:
 1. SolrSearchServlet - Servlet.
 2. SolrSearchHelper
 3. PageService - Service to get Page Data for Index.
 4. SolrServiceAPI - Make Rest Call to Solr Server.
 5. SolrOSGIConfig - Factory OSGI Config.
 6. SolrOSGIConfigService - Service to get OSGI Configuration.
 7. SolrServiceManager - Get OSGI Config for specific site.
 8. SolrOSGISchedulerConfig - OSGI config for Scheduler
 9. SolrOSGISchedulerConfigService - Get OSGI Configuration for Scheduler.
 10. SolrCAConfig - Context Aware Configuration.

 HighLevel Flow:
 1. Servlet/Scheduler/EventListener -> will create object of SolrSearchHelper.
 2. SolrSearchHelper -> Gets the current siteID based on the context(CA) using SolrCAConfig.
 3. Using the siteID, SolrSearchHelper gets the corresponding solrEndpoint using SolrServiceManager.
 4. SolrSearchHelper makes the REST call to Solr.

 HighLevel Flow For Servlet:
 1. SolrSearchServlet -> calls services PageService,SolrServiceManager,SolrServiceAPI
 2. SolrSearchServlet -> Create SolrSearchHelper object with passing SolrServiceManager inside constructor.

b) Install Solr - Unzip the file .
1. Run solr start in /bin
2. Access http://localhost:8983
3. Add core:
   name: techproducts
   instanceDir: D:\world\software\solr-8.11.2\solr-8.11.2\server\solr\configsets\sample_techproducts_configs
4. Copy the sample data to this core:
   Go to D:\world\software\solr-8.11.2\solr-8.11.2\example\exampledocs
   Run the command -> java -jar -Dc=techproducts post.jar *.xml
5. Create two cores:
    a). Create folders weretail and wknd under D:\world\software\solr-8.11.2\solr-8.11.2\server\solr
    b). Copy data and conf folder folders from D:\world\software\solr-8.11.2\solr-8.11.2\server\solr\configsets\sample_techproducts_configs
        and paste under weretail and wknd folders.
    c)  Empty the data folder for both wknd and weretail.
    d) From solr admin, click add core and enter name and dir as weretail.
       Repeat same for wknd.
6. Confugure OSGI service and CA,hit the servlet - http://localhost:4502/bin/solrsearch?searchParameter=index&sitePath=/content/sumit/us/en

---------------------AEM + Solr-------------------------
1. Install jenkins war and run the command java -jar Jenkins.war
2. Default port is 8080. To change the port java -jar Jenkins.war --httpPort-9090
3. Visit localhost:8080 and enter password(7ff2bbbce2ce41848f5e19c9315a529c). 
   Then create an admin user . username and password as admin.
4. Integrate Public GitHub Repo:
   a. New Item > Enter Project Name > Freestyle Project
   b. Check Github Project and enter Github URL (eg: https://github.com/sumitsapient/javaeight)
   c. Select Git in Source Code Management.
   d. Enter Github Repository URL (https://github.com/sumitsapient/javaeight.git) and select branch (main)
   e. Select Delete workspace before build starts in Build Environment.
   f. Select Invoke top-level Maven targets in Build Steps.
   g. Enter goal as clean install.
5. Integrate Private Github Repo:
   a. It can be integrated either by Personal Access Token or SSH Keys.
   b. Below steps are for Personal Access Token.
   c. In github create personal access tokens and in Jenkins create credentials in Jenkins.
6. 

## AEM Scratch
-------------------------------------------AEM FROM SCRATCH_______________

1. Create a project using maven archetype(version was 35) and deploy the package using $ mvn clean install -PautoInstallSinglePackage.
   mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate -DarchetypeGroupId=com.adobe.granite.archetypes -DarchetypeArtifactId=aem-project-archetype -DarchetypeVersion=15 -DarchetypeCatalog=https://repo.adobe.com/nexus/content/groups/public/

2. This will deploy 4 packages- ***apps,***config,***content,***all.

3. The generated AEM project is made up of individual Maven modules, each with a different role.
   core - Java Code, primarily back-end developers.
   frontend - Contains source code for CSS, JavaScript, Sass, Type Script, primarily for front-end developers.
   apps - Contains component and dialog definitions, embeds compiled CSS and JavaScript as client libraries.
   content - contains structural content and configurations like editable templates, metadata schemas (/content,%20/conf?lang=en).
   all - this is an empty Maven module that combines the above modules into a single package that can be deployed to an AEM environment.

4. The core module contains all of the Java code associated with the project. When built it deploys an OSGi bundle to AEM. To build just this module:
   $ mvn clean install -PautoInstallBundle
   Similarly apps and content modules can be deployed:
   $ mvn clean install -PautoInstallPackage

5. Create Editable template. Archetype creates empty page template. We can leverage it and create our template.  
   Editable template has three main areas: structure,initialcontent,policies.
   Create a page using newly created template and use XF as header and footer.

6. Client-Side Libraries provides a mechanism to organize and manage CSS and JavaScript files necessary for an AEM Sites implementation.
   The basic goals for client-side libraries or clientlibs are:
   Store CSS/JS in small discrete files for easier development and maintenance.
   Manage dependencies on 3rd party frameworks in an organized fashion.
   Minimize the number of client-side requests by concatenating CSS/JS into one or two requests.

   Note- css and js can be minified using Adobe Granite HTML Library Manager.

7. Client-side libraries have some limitations when it comes to support of languages like Sass or TypeScript.
   There are a number of open-source tools like NPM and webpack that accelerate and optimize front-end development.
   The goal of the ui.frontend module is to be able to use these tools to manage a majority of front-end source files.

8. main.scss under src/main/webpack/site is the entry point for all SASS files in frontend module.

9. main.ts is the entry point used by webpack. main.ts includes main.scss and a regular expression to collect any .js or .ts files in the project.

10.Inspect the files beneath src/main/webpack/site/styles:
These files styles for global elements in the template, like the Header, Footer and main content container. The CSS rules in these files target different HTML elements header, main, and footer.

11. The important clientlibs folder generated by archetype are
    clientlib-base - Base level of CSS and JavaScript needed for WKND Site to function(embeds Core Component client libs)
    clientlib-site - Contains site-specific theme for the WKND Site (Generated by the ui.frontend module)
    clientlib-dependencies - Embeds any 3rd party dependencies  (Generated by the ui.frontend module)

In our page component we'll include clientlib-base as it contains core component clientlibs.
In customheaderlibs.html, we will use css i.e
<sly data-sly-use.clientLib="/libs/granite/sightly/templates/clientlib.html">
<sly data-sly-call="${clientlib.css @ categories='sumit.base'}">
</sly>
In customfooterlibs.html, we will use JS i.e
<sly data-sly-use.clientlib="/libs/granite/sightly/templates/clientlib.html">
<sly data-sly-call="${clientlib.js @ categories='sumit.base'}"/>
</sly>

And remaining clientlib-site and clientlib-dependencies will be generated by frontend module and inserted into clientlibs inside /apps.
To include this, go to editable template structure(eg: ArticlePage Template) -> Click on Page Information -> Page Policy -> Properties Tab
and add both clientlibs.
***Note***
A key integration piece built into the ui.frontend module, aem-clientlib-generator takes the compiled CSS
and JS artifacts from a webpack/npm project and transforms them into AEM client-side libraries.

How it works?
1. Run npm install in frontend module.
2. Start the webpack dev server in watch mode by running the following command:$ npm run watch.
   This will compile the src files in the ui.frontend module and sync the changes with AEM at http://localhost:4502
   The command npm run watch ultimately populates the clientlib-site and clientlib-dependencies in the ui.apps module
   which is then synced automatically with AEM.

3. This will create site.css and site.js in frontend/dist/clientlib-site/
   These file will be copied to apps/src/main/content/jcr_root/apps/wknd/clientlibs/clientlib-site

4. Inspect the file ui.frontend/clientlib.config.js. This is the configuration file for an npm plugin,
   aem-clientlib-generator that transforms the contents of /dist into a client library and moves it to the ui.apps module.


12. The Style System allows developers and template editors to create multiple visual variations of a component.
    The general idea with the Style System is that authors can choose various styles of how a component should look.
    The “styles” are backed by additional CSS classes that are injected into the outer div of a component.
    In the client libraries CSS rules are added based on these style classes so that the component changes appearance.

13. To create a Underline Style for Title component:
    1. Go to Structure of ArticlePage Template and click on Policy Icon of Title Component.
    2. Go to Style Tab and add Styles eg: Underline(i.e name that will appear when we click on paint brush) cmp-title--underline(i.e css class).
    3. Add this css class in our _title.scss file in frontend module and npm run watch, this will insert the css in /apps .
    4. Go to Title Component and click on paint brush and select "Underline" to apply this style in Title Component.

14. Nodes created during custom component creation.
    1. Create byline folder add a new file named .content.xml  (this XML file provides the definition for the component, including the title, description and group.)
    2. Beneath the byline folder, add a new file byline.html (it's responsible for the HTML presentation of the component.)
       Naming the file the same as the folder is important, as it becomes the default script Sling will use to render this resource type.
    3. Create the Dialog definition - Beneath the byline folder, create a new folder named _cq_dialog.
       Beneath byline/_cq_dialog add a new file named .content.xml(This is the XML definition for the dialog.)
    4. Create the Policy dialog - Beneath the byline folder, create a new folder named _cq_design_dialog.
       Beneath byline/_cq_design_dialog create a new file named .content.xml

***Note***
sling:resourceSuperType="core/wcm/components/image/v2/image"
These dialog node definitions use the Sling Resource Merger to control which dialog tabs are inherited from the sling:resourceSuperType
component, in this case the Core Components’ Image component., Sling Resource Merger is used to hide irrelevant fields that are otherwise inherited from the sling:resourceSuperType,
as seen by the node definitions with sling:hideResource="{Boolean}true" property.

15. Creating Byline Sling Model - Sling Models are annotation driven Java “POJO’s” (Plain Old Java Objects) that facilitate the mapping of data from the JCR to Java variables.
    The Byline Sling Model will rely on several Java APIs provided by AEM. These APIs are made available via the dependencies listed in the core module’s POM file.
    In pom.xml of core- For AEM as a Cloud Service Only "aem-sdk-api" is added.
    For AEM 6.5/6.4 "uber-jar" is added.

    1. Create ByLine.java interface and its implementation ByLineImpl.java.
    2. Annotate ByLineImpl with
       @Model(
       adaptables = {SlingHttpServletRequest.class},
       adapters = {Byline.class},
       resourceType = {BylineImpl.RESOURCE_TYPE},
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
       )
       ***The @Model annotation registers BylineImpl as a Sling Model when it is deployed to AEM.
       ***The adaptables parameter specifies that this model can be adapted by the request.
       ***The adapters parameter allows the implementation class to be registered under the Byline interface.
       This allows the HTL script to call the Sling Model via the interface (instead of the impl directly).
       ***The resourceType points to the Byline component resource type (created earlier) and helps to resolve the correct model if there are multiple implementations.
       3.Since Sling Models are Java POJO’s, and not OSGi Services, the usual OSGi injection annotations @Reference cannot be used, instead Sling Models provide a special @OSGiService annotation that provides similar functionality.
    4. @PostConstruct is incredibly useful and acts in a similar capacity as a constructor, however, it is invoked after the class is instantiated and all annotated Java fields are injected.
       Whereas other Sling Model annotations annotate Java class fields (variables),
       @PostConstruct annotates a void, zero parameter method, typically named init() (but can be named anything).

16. In byline.html we created in the earlier set up of the AEM Component:
     <div data-sly-use.placeholderTemplate="core/wcm/components/commons/v1/templates.html">
     </div>
     <sly data-sly-call="${placeholderTemplate.placeholder @ isEmpty=false}"></sly>
    1. The placeholderTemplate points to Core Components’ placeholder, which displays when the component is not fully configured.
     This renders in AEM Sites Page Editor as a box with the component title, as defined above in the cq:Component’s jcr:title property.
    2. The data-sly-call="${placeholderTemplate.placeholder @ isEmpty=false} loads the placeholderTemplate defined above and passes in a boolean value (currently hard-coded to false) into the placeholder template.
    	When isEmpty is true, the placeholder template renders the grey box, else it renders nothing. 

    <!--/* byline.html */-->
   <div data-sly-use.byline="com.adobe.aem.guides.wknd.core.models.Byline"
    data-sly-use.placeholderTemplate="core/wcm/components/commons/v1/templates.html"
    data-sly-test.hasContent="${!byline.empty}"
    class="cmp-byline">
    <div class="cmp-byline__image"
        data-sly-resource="${ '.' @ resourceType = 'core/wcm/components/image/v2/image' }"> /* This data-sly-resource, included the current resource via the relative path '.', and forces the inclusion of the current resource (or the byline content resource) with the resource type of core/wcm/components/image/v2/image.*/
    </div>
    <h2 class="cmp-byline__name">${byline.name}</h2>
    <p class="cmp-byline__occupations">${byline.occupations @ join=', '}</p>
   </div>
   <sly data-sly-call="${placeholderTemplate.placeholder @ isEmpty=!hasContent}"></sly>





-------------------------------------------AEM FROM SCRATCH_______________

1.Create a project using maven archetype(version was 35) and deploy the package using
$ mvn clean install -PautoInstallSinglePackage. mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate -DarchetypeGroupId=com.adobe.granite.archetypes -DarchetypeArtifactId=aem-project-archetype -DarchetypeVersion=15 -DarchetypeCatalog=https://repo.adobe.com/nexus/content/groups/public/

2. This will deploy 4 packages- ***apps,***config,***content,***all.

3. The generated AEM project is made up of individual Maven modules, each with a different role.
   core - Java Code, primarily back-end developers.
   frontend - Contains source code for CSS, JavaScript, Sass, Type Script, primarily for front-end developers.
   apps - Contains component and dialog definitions, embeds compiled CSS and JavaScript as client libraries.
   content - contains structural content and configurations like editable templates, metadata schemas (/content,%20/conf?lang=en).
   all - this is an empty Maven module that combines the above modules into a single package that can be deployed to an AEM environment.

4. The core module contains all of the Java code associated with the project.
   When built it deploys an OSGi bundle to AEM. To build just this module: $ mvn clean install -PautoInstallBundle .
   Similarly apps and content modules can be deployed: $ mvn clean install -PautoInstallPackage

5. Create Editable template. Archetype creates empty page template. We can leverage it and create our template.
   Editable template has three main areas: structure,initialcontent,policies.
   Create a page using newly created template and use XF as header and footer.

6. Client-Side Libraries provides a mechanism to organize and manage CSS and JavaScript files necessary for an AEM Sites implementation.
   The basic goals for client-side libraries or clientlibs are: Store CSS/JS in small discrete files for easier development and maintenance.
   Manage dependencies on 3rd party frameworks in an organized fashion. Minimize the number of client-side requests by concatenating CSS/JS into one or two requests.

Note- css and js can be minified using Adobe Granite HTML Library Manager.

7. Client-side libraries have some limitations when it comes to support of languages like Sass or TypeScript.
   There are a number of open-source tools like NPM and webpack that accelerate and optimize front-end development.
   The goal of the ui.frontend module is to be able to use these tools to manage a majority of front-end source files.

main.scss under src/main/webpack/site is the entry point for all SASS files in frontend module.

main.ts is the entry point used by webpack. main.ts includes main.scss and a regular expression to collect any .js or .ts files in the project.

10.Inspect the files beneath src/main/webpack/site/styles: These files styles for global elements in the template, like the Header, Footer and main content container.
The CSS rules in these files target different HTML elements header, main, and footer.

The important clientlibs folder generated by archetype are
clientlib-base - Base level of CSS and JavaScript needed for WKND Site to function(embeds Core Component client libs)
clientlib-site - Contains site-specific theme for the WKND Site (Generated by the ui.frontend module)
clientlib-dependencies - Embeds any 3rd party dependencies (Generated by the ui.frontend module)
In our page component we'll include clientlib-base as it contains core component clientlibs.
In customheaderlibs.html, we will use css i.e In customfooterlibs.html, we will use JS i.e

And remaining clientlib-site and clientlib-dependencies will be generated by frontend module and inserted into clientlibs inside /apps.
To include this, go to editable template structure(eg: ArticlePage Template) -> Click on Page Information -> Page Policy -> Properties Tab and add both clientlibs. Note A key integration piece built into the ui.frontend module, aem-clientlib-generator takes the compiled CSS and JS artifacts from a webpack/npm project and transforms them into AEM client-side libraries.

How it works?

Run npm install in frontend module.

Start the webpack dev server in watch mode by running the following command:$ npm run watch. This will compile the src files in the ui.frontend module and sync the changes with AEM at http://localhost:4502 The command npm run watch ultimately populates the clientlib-site and clientlib-dependencies in the ui.apps module which is then synced automatically with AEM.

This will create site.css and site.js in frontend/dist/clientlib-site/ These file will be copied to apps/src/main/content/jcr_root/apps/wknd/clientlibs/clientlib-site

Inspect the file ui.frontend/clientlib.config.js. This is the configuration file for an npm plugin, aem-clientlib-generator that transforms the contents of /dist into a client library and moves it to the ui.apps module.

The Style System allows developers and template editors to create multiple visual variations of a component. The general idea with the Style System is that authors can choose various styles of how a component should look. The “styles” are backed by additional CSS classes that are injected into the outer div of a component. In the client libraries CSS rules are added based on these style classes so that the component changes appearance.

To create a Underline Style for Title component:

Go to Structure of ArticlePage Template and click on Policy Icon of Title Component.
Go to Style Tab and add Styles eg: Underline(i.e name that will appear when we click on paint brush) cmp-title--underline(i.e css class).
Add this css class in our _title.scss file in frontend module and npm run watch, this will insert the css in /apps .
Go to Title Component and click on paint brush and select "Underline" to apply this style in Title Component.
Nodes created during custom component creation.

Create byline folder add a new file named .content.xml (this XML file provides the definition for the component, including the title, description and group.)
Beneath the byline folder, add a new file byline.html (it's responsible for the HTML presentation of the component.) Naming the file the same as the folder is important, as it becomes the default script Sling will use to render this resource type.
Create the Dialog definition - Beneath the byline folder, create a new folder named _cq_dialog. Beneath byline/_cq_dialog add a new file named .content.xml(This is the XML definition for the dialog.)
Create the Policy dialog - Beneath the byline folder, create a new folder named _cq_design_dialog. Beneath byline/_cq_design_dialog create a new file named .content.xml
Note sling:resourceSuperType="core/wcm/components/image/v2/image" These dialog node definitions use the Sling Resource Merger to control which dialog tabs are inherited from the sling:resourceSuperType component, in this case the Core Components’ Image component., Sling Resource Merger is used to hide irrelevant fields that are otherwise inherited from the sling:resourceSuperType, as seen by the node definitions with sling:hideResource="{Boolean}true" property.

Creating Byline Sling Model - Sling Models are annotation driven Java “POJO’s” (Plain Old Java Objects) that facilitate the mapping of data from the JCR to Java variables. The Byline Sling Model will rely on several Java APIs provided by AEM. These APIs are made available via the dependencies listed in the core module’s POM file. In pom.xml of core- For AEM as a Cloud Service Only "aem-sdk-api" is added. For AEM 6.5/6.4 "uber-jar" is added.

Create ByLine.java interface and its implementation ByLineImpl.java.
Annotate ByLineImpl with @Model( adaptables = {SlingHttpServletRequest.class}, adapters = {Byline.class}, resourceType = {BylineImpl.RESOURCE_TYPE}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL ) ***The @Model annotation registers BylineImpl as a Sling Model when it is deployed to AEM. ***The adaptables parameter specifies that this model can be adapted by the request. ***The adapters parameter allows the implementation class to be registered under the Byline interface. This allows the HTL script to call the Sling Model via the interface (instead of the impl directly). ***The resourceType points to the Byline component resource type (created earlier) and helps to resolve the correct model if there are multiple implementations. 3.Since Sling Models are Java POJO’s, and not OSGi Services, the usual OSGi injection annotations @Reference cannot be used, instead Sling Models provide a special @OSGiService annotation that provides similar functionality.
@PostConstruct is incredibly useful and acts in a similar capacity as a constructor, however, it is invoked after the class is instantiated and all annotated Java fields are injected. Whereas other Sling Model annotations annotate Java class fields (variables), @PostConstruct annotates a void, zero parameter method, typically named init() (but can be named anything).
In byline.html we created in the earlier set up of the AEM Component:

1. The placeholderTemplate points to Core Components’ placeholder, which displays when the component is not fully configured. This renders in AEM Sites Page Editor as a box with the component title, as defined above in the cq:Component’s jcr:title property. 2. The data-sly-call="${placeholderTemplate.placeholder @ isEmpty=false} loads the placeholderTemplate defined above and passes in a boolean value (currently hard-coded to false) into the placeholder template. When isEmpty is true, the placeholder template renders the grey box, else it renders nothing.
   /* This data-sly-resource, included the current resource via the relative path '.', and forces the inclusion of the current resource (or the byline content resource) with the resource type of core/wcm/components/image/v2/image.*/














--------JAVA OOPs Concepts---------
1. Explain briefly OOPs Concept? - Object
   Class,Inheritance,Polymorphism,Abstraction,Encapsulation

2. Why static keyword is used? - static keyword is used for memory management mainly. We can apply static keyword with variables, methods
   The static variable can be used to refer to the common property of all objects (eg: static String college="IELTS")

3. Can a class extend multiple class? - Multiple inheritance is not allowed.

------------------Collections-----------
1. Basic interfaces of the Java Collection Framework? - Set,List,Collection

2. ArrayList vs LinkedList?- ArrayList is preferred when storing and accessing is required while Linked list is prefered when manipulation is required.

3. Why do we use Map in Java?

----------------Exception-------
1. How the exceptions are handled in java?using try, catch and finally blocks
2. what are checked and unchecked exceptions in java? Checked exceptions are the exceptions which are known to compiler.These exceptions are checked at compile time only. ClassNotFoundException, SQLException, IOException

                                                      Unchecked exceptions are those exceptions which are not at all known to compiler. These exceptions occur only at run time.NullPointerException, ArrayIndexOutOfBoundsException, NumberFormatException

----------------Stream API------
1. Stream API is used to process collections of objects.
2. Using stream API , get the list starting with "S"
   List names = Arrays.asList("Reflection","Collection","Stream");
   List result = names.stream().filter(s->s.startsWith("S")).collect(Collectors.toList());												  
   3.Lambda expressions are similar to methods, but they do not need a name and they can be implemented right in the body of a method.
   4.Serialization is a mechanism of converting the state of an object into a byte stream.

---------HTML/CSS/JavaScript---------
1. difference between  let and const.
2. forEach vs Map?
3. worked on javascript library like React or framework like Angular.
   4.jQuery is a popular library for simiplifying interactions between DOM and JavaScript

--------REST API------------
Worked on REST API?

---------AEM--------
1.What is maven archetype/How do u create project template in AEM?- The AEM Project Archetype is a Maven template that creates a minimal, best-practices-based Adobe Experience Manager.
2.AEM architecture stack of AEM.
3.Have you worked on sling models? DefaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
@Model(adaptables = SlingHttpServletRequest.class,
adapters = AccordionCmp.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

4.
@Model(adaptables = Resource.class)
public class TestModel {

    // Injects the child of the resource using ChildResource annotation
    @ChildResource(name="content")
    Resource child;

    public String getChildPath() {
      return  child.getPath();
    }
}
ChildResource : This injector is adaptable to resource and is used to get the specific child of a resource.

5. Script Variable: This Injector is used to get the currentPage, PageManager, Design, PageProperties etc.
   This injector is adaptable to request.This injector is adaptable to SlingHttpServletRequest.
   @Model(adaptables = SlingHttpServletRequest.class)
   public class TestModel {

// Injects currentPage using ScriptVariable annotation
@ScriptVariable(name="currentPage")
Page page;

    public String getPagePath() {
        return  page.getPath();
    }
}

6. Template Types are effectively a template for a template.

7. Where is template type stored:
   /conf/wknd/settings/wcm/template-types/empty-page/structure/jcr:content
   8.responsive layout for your pages??
   AEM allows you to have a responsive layout for your pages by using the Layout Container component.
   This provides a paragraph system that allows you to position components within a responsive grid.
   This grid can rearrange the layout according to the device/window size and format.

9.Style System?
Template authors not only need the ability to configure how components function for the content authors,
but also to configure a number of alternative visual variations of a component.


4.Diff btw Felix SCR annotations and OSGi R6 annotations?
5. How do u create configurable service in AEM using DS annotations.
6. How do u create servlet using DS?
   @Component(
   service = Servlet.class,
   property = {
   "sling.servlet.extensions=html",
   "sling.servlet.selectors=foo",
   "sling.servlet.paths=/bin/foo",
   "sling.servlet.paths=/bin/bar",
   "sling.servlet.methods=get",
   "sling.servlet.resourceTypes=nt:file",
   "sling.servlet.resourceTypes=project/components/component"
   }
   )
7. Worked on Event Listener- We can achieve event handling at the JCR level by implementing the EventListener interface in a class.Override onEvent method.
   @Component(service = EventListener.class, immediate = true)
   public class CustomEventListener implements EventListener {...}
8. How do i get the resource progammatically in AEM.
9.  Sling API------Resource
    JCR API----Node
    adaptTo = Sling comes with adapters mechanism. One object can be adopted to other.
    ValueMap properties = resource.adaptTo(ValueMap.class);
    String property = properties.get("myProperty", String.class);


A developer needs to change the label “Sites” in the navigation of AEM author to “Websites”
Modify the node /libs/cq/core/content/nav/sites by updating the icr:title property value.

A developer creates an AEM editable template that includes a Layout Container.
When the developer creates a page using this template, the Layout Container placeholder does NOT appear.
The Layout Container has NOT been unlocked.

You want to restrict the size of a custom log file and enable log file rotation. Which Apache
Sling configuration has to be changed in the AEM Web Console?
Apache Sling Logging Logger Configuration.

The model must check if the configured value of the jcr:title property for the component matches the name of the current page. If the jcr:title property of the component has NOT been configured, then isMatchingTitle() must
return false

6. What are the requests not cached by the dispatcher?
   Request that does not return http code 200
   Requests with suffixes
   Requests with request parameter(i.e ?)
>Programmatically: send an http header response.setHeader("Dispatcher", "no-cache")

18. How to prevent one particular component to be cached, while you are caching the whole page into dispatcher?
    We have to configure the component(which we don't want to be cached) into Sling Dynamic Include (SDI).

The /renders property defines the URL to which Dispatcher sends requests to render a document.
/renders {
/0001 {
/hostname "host-name-here"
/port "4502"
/ipv4 "1"
/always-resolve "1"
}
}

Permission-sensitive caching enables you to cache secured pages.
Dispatcher checks user’s access permissions for a page before delivering the cached page.
Dispatcher includes the AuthChecker module that implements permission-sensitive caching.
{
# request is sent to this URL with '?uri=<page>' appended
/url "/bin/permissioncheck"

# only the requested pages matching the filter section below are checked,
# all other pages get delivered unchecked
/filter
{
/0000
{
/glob "*"
/type "deny"
}
/0001
{
/glob "/content/secure/*.html"
/type "allow"
}
}
# any header line returned from the auth_checker's HEAD request matching
# the section below will be returned as well
/headers
{
/0000
{
/glob "*"
/type "deny"
}
/0001
{
/glob "Set-Cookie:*"
/type "allow"
}
}

A developer is creating a custom component on the page /latestBlogs.html that needs to list all the titles of the blogs pages under /content/blogs.
How does this component get the list of child pages?
Use PageManager.getPage(“/content/blogs”) of the static PageManager class to instantiate a Page object and then iterate through the child pages and print the title for each


A developer has a component located under the path /apps. This component has a Client Library which is directly loaded onto a page.
The publish instance loads the page correctly. The dispatcher also loads the page but the Client Library is missing.
How should the developer resolve this issue, while taking security into consideration?
Add the property allowProxy with a boolean value true

One of the most important meta files of a vault checkout or a content package is the filter.xml which is present in the META-INF/vault directory.
The filter.xml consists of a set of filter elements, each with a mandatory root attribute and an optional list of include and exclude child elements.
MERGE: Existing content is not modified, i.e. only new content is added and none is deleted or modified.
UPDATE: Existing content is updated, new content is added and none is deleted.
REPLACE: This is the normal behavior. Existing content is replaced completely by the imported content, i.e. is overridden or deleted accordingly.


How to run some configuration specific to AEM instance.
create node -> apps/project/config.author.dev
create child node -> type will be sling:OSGIConfig and name should be same as PID

Run  component on specific Environment eg. publish
Create component and annotate with policy =ConfigurationPolicy.REQUIRE
create node under config.publish-> com.sumit.MyComponentnz(slingOsgiConfig)

BundleA for author and Bundle B for publish.
install.author -> BundleA.jar
install.publish-> BundleB.jar


 <span data-sly-text="${properties.title}"> Some text</span>

 <span data-sly-attribute.title="${properties.title}" title="Lorem Ipsum"> Hello World</span>

 <h1 data-sly-element="p">Lorem Ipsum</h1>

data-sly-list vs data-sly-repeat

 <ul data-sly-repeat="${currentPage.listChildren}">

 </ul>

1. Assuming we have resource /x/y, resolve below URI:
   /x/y.s1.s2.html/a/b.s.txt		
   Resource - /x/y
   Selector - s1.s2
   Extension- html
   Suffix - /a/b.s.txt
   Resource Found - Yes

2. Using querybuilder- Search for pages tagged with a certain tag.
   type=cq:Page
   tagid=marketing:interest/product
   tagid.property=jcr:content/cq:tags

3. How to use QueryBuilder API

a. create query desc Hashmap
b. Query query = builder.createQuery(PredicateGroup.create(map), session);
c. SearchResult result = query.getResult();

4. How to include JSP in your HTL file.
   <sly data-sly-include="template.jsp"/>

5. Can you create a component without cq:dialog?
   Without cq:dialog the component cannot be added to page.


Problem Statement: In the Touch UI Dialogs ,If there is a need to put some validation on fields or we just want to add some CSS,
then use “cq.authoring.dialog” clientLibrary.This clientlibs is automatically loaded when the dialog opens.
So, No need to make an entry of it on any page.

This created a performance issue because if the clientLibrary is so big and it is getting loaded in all dialogs regardless of dialog needs it or not.

Solution:

Using extraClientlibs Property: In the cq:dialog node of a dialog, Add these properties:
1. Name:extraClientlibs
Type: String[]
Value: ClientLib_Category_Name
2. Name: mode
Type:String
Value:edit”

6. ----------------Sling Model Exporter--------------------

   @Exporter(name="exporter",extensions="type",selector="selector")		  
   name-> of the exporter
   extensions-> which format the exporter will return.
   selector->
   Eg: Jackson is OOTB Exporter provided by AEM:
   @Exporter(name="jackson",extensions="json",selector="geek")
   model - is default selector
   http://localhost:4502/content/sumit/us/en/magazine/prayagraj-skateboards/jcr:content/root/container/container/aemgeek.geek.json

6.1 It exports all getter method in JSON format.
6.2 To export non getter method, use @JsonProperty.
@JsonProperty(value="scholName")
public String school()
{
return "SMC";
}
6.3 To change the name of Json Key, we use @JsonProperty in getter method:

@JsonProperty(value="teacher")
public boolean getProfessor() {
return professor;
}
6.4 If any value is null, we use @JsonIgnore to not produce that json key.
6.5 To wrap all json object in some root json key. We use options attribute in @Exporter
and annotate the class with @JsonRootName("rootKey")
@Exporter(name="jackson",extensions="json",selector="geek",options={
@ExporterOption(name="SerializationFeature.WRAP_ROOT_VALUE",value="true")})
@JsonRootName("rootKey")
6.6 To write custom Sling Model Exporter- i.e XML exporter(We will use JAXB API)
We need to create AEM Service implementing ModelExporter.class and override 3 methods:
1. boolean isSupported
2. String getName
3. export
6.7 We can have multiple exporters with @Exporters option- Refer AEMGeekImpl.java

7.------------Service User-------------

7.1 Create System User using /crx/explorer/index.jsp and give permissions in user admin for this user.
Or Create a sysem user  and add permissions using ACS Commons Config -> Ensure Service User
7.2 Map this user in AEM config -> Apache Sling Service User Mapper Service Amendment
{bundleName}:{servicename}={serviceuser}
aem-sumit.core:sumitservice=sumit-system-user
7.3 Write Util class to get resource resolver. Refer ResourceResolverUtil.java

8.------------Logging--------------------

8.1 We have 5 log levels in order as:
Trace
Debug
Info
Warn
Error
8.2 Custom log files can be created to log specific package or class using config -> Apache Sling Logging Logger Configuration.
8.3 AEM provides console to view/add logs in browser -> http://localhost:4502/system/console/slinglog
8.4 AEM provides config to configure log rotation based on file size and time -> Apache Sling Logging Writer Configuration.

9------------OSGI Service--------------------

9.1 Service will always implement interface
Mandatory annotation -> @Component(service=SomeInterface.class)
Optional annotation -> @Activate , @Deactivate

9.2 When we have two implementation of same interface, we can define ranking of osgi service.

     MultiService(Interface) -> getName()
	 MultiServiceImplA() -> Implementation of getName()
	 MultiServiceImplB() -> Implementation of getName()
	 
	 When we use this service , by default priority will be given to service with lower Service ID.
     Service ID can be found in AEM console when we click core bundle.

9.3 But we can give ranking to each implementation to call them separately by annotating service with @ServiceRanking(1001).
eg:
@Component(service= MultiService,name=ServiceA)
@Ranking(1001)
MultiServiceImplA()

9.4 To call specific service in sling Model, we use filter attribute. Below calls MultiServiceImplB.
@OsgiService(filter="(component.name=ServiceB)")
MultiService service;

9.5 To call specific service in Component, we use target attribute.
@Reference(target="(component.name=ServiceB)")

10-----------OSGI COnfig using r7 annotation--------

10.1 Create @interface GeeksOSGiConfig and annotate with @ObjectClassDefinition(name="Modular OSGi Configuration",description = "Modular OSGi Configuration demo.")
This interface will map all values from configMgr using @AttributeDefinition
10.2 Create OSGI Service and annotate with @Designate(ocd=GeeksOSGiConfig.class) and inject above config in @Activate Method.

11-----------OSGI Factory Configuration-------------

When we need different instance of same OSGI configuration, we declare OSGI config as factory configuration.
This is achieved by adding attribute factory=true in @Designate .

11.1 Inside Impl we create new List:
private List<OSGiFactoryConfig> factoryConfigsList; //OSGiFactoryConfig -> is our interface with all config mapping
11.2 We add bind and unbind method as:

      @Reference(service = OSGiFactoryConfig.class,cardinality = ReferenceCardinality.MULTIPLE,policy = ReferencePolicy.DYNAMIC)
      public void bindOSGIFactoryConfig( final OSGiFactoryConfig osGiFactoryConfig) {
    
       factoryConfigsList.add(osGiFactoryConfig);
      }

      public void unbindOSGIFactoryConfig( final OSGiFactoryConfig osGiFactoryConfig) {
       factoryConfigsList.remove(osGiFactoryConfig);
      }
11.3 ReferenceCardinality.MULTIPLE -> This method will listen to service everytime.
ReferencePolicy.DYNAMIC -> It will find unbind method with same name automatically.

12----------------Sling Servlet---------------

Servlet can be registered using OSGi DS 1.4(R7) which needs bnd-maven-plugin 4.0+ -> @SlingServletResourceTypes ,@SlingServletPaths
Servlet can be registered using OSGi DS 1.2(RS) which needs mvn-bundle-plugin 3.0+ -> @Component(service=Servlet.class)
Servlets can be registered using resourceType or path.

12.1 Using resourceType: Servlet is bound to resource and can be access controlled using JCR repository ACL.
Servlets use methods,selector and extensions.

13---------------Sling Schedular-------------

Schedular can be registered by implementing either:
13.1 Runnable Interface -> implement run method
13.2 Job Interface -> implement execute(JobContext ctx)
13.3 Status of scheduler can be checked at http://localhost:4502/system/console/status-slingscheduler

We need to implement JobInterface if we want to run scheduler in different time intervals with different parameters.

14-------------QueryBuilder------------------

Two search API's(Repository Search) used in AEM are - QueryBuilder API and JCR SQL2
AEM Console to execute query -> http://localhost:4502/libs/cq/search/content/querydebug.html

14.1 Whatever we want to search we provide in predicate called type and value should be the primaryTpe.
Bydefault AEM will restrict result to 10 results but we can use p.limit = -1 to show all results.

14.2 Search all pages under given path
type = cq:Page
path = /content/sumit/
orderby = @jcr:content/cq:lastModified
orderby.sort = desc
p.limit= -1


	  Search page where component is used:
	  type = nt:unstructured
      path = /content/amarr/com/
      property = sling:resourceType
      property.value = amarr/components/content/webcomponents/dealer/dealerdoordesignerprogram/v1/dealerdoordesignerprogram

14.3 To search a property we use property predicate.
Search all pages with given template
type = cq:Page
path = /content/sumit/
property = jcr:content/cq:template
property.value = /conf/sumit/settings/wcm/templates/page-content

14.4 To search multiple values with given property:
By default its an OR opertaion when we search single property. It will find either title is Magazine or title is FAQ's
Search all pages with jcr title Magazine or FAQs
type = cq:Page
path = /content/sumit/
property = jcr:content/jcr:title
property.1_value = Magazine
property.2_value = FAQs

	  To search with AND operation on same property -> property.and = true

14.5 To search multiple property:
By default its an AND operation when searching multiple property.
type = cq:Page
path = /content/sumit/
1_property = jcr:content/jcr:title
1_property.value = Magazine
2_property = jcr:content/cq:template
2_property.value = /conf/sumit/settings/wcm/templates/page-content

	  In order to apply OR operation while multiple property search operation , we use predicate group.
	  type = cq:Page
      path = /content/sumit/
	  group.p.or = true
      group.1_property = jcr:content/jcr:title
      group.1_property.value = Magazine
      group.2_property = jcr:content/cq:template
	  group.2_property.value = /conf/sumit/settings/wcm/templates/page-content

	  When we search with above query, you can see the default property returned by JCR in JSON QueryBuilder Link.
	  eg: For above query we see result as:
	  {
        path: "/content/sumit/us/en/magazine",
        excerpt: "",
        name: "magazine",
        title: "Magazine",
        lastModified: "2022-05-23 15:47:00",
        created: "2022-03-06 21:36:18"
      }
	  We can alter this behavior to show properties according to our requirement.
14.6 p.hits = full -> will give full property name. Eg: above result will look like->
{
"jcr:path": "/content/sumit/us/en/magazine",
"jcr:primaryType": "cq:Page",
"jcr:createdBy": "admin",
"jcr:created": "Sun Mar 06 2022 21:36:18 GMT+0530"
}

14.7 To get selective properties:
p.hits = selective
p.properties = jcr:content/sling:resourceType

	 eg: above will become as
	 {
      "jcr:content": {
           "sling:resourceType": "sumit/components/page"
                    }
     }

14.8 To search property tags, we can make use of predicate called tagid.
type = cq:Page
path = /content/sumit
tagid.property = jcr:content/cq:tags
tagid = we-retail:activity/swimming

14.9 We make use of predicate daterange to search for dates

      type = cq:Page
      path = /content/sumit
      daterange.property = jcr:content/cq:lastModified
      daterange.lowerBound = 2021-12-11
      daterange.upperBound = 2022-12-11 

14.10 If a query result exceeds more eg: 200+ ,
p.limit = 20 -> will always give 20 result
We can combine p.limit with another predicate called p.offset to fetch next values eg:
p.offset = (n-1) -> we pass value of n from frontend i.e 1,2 etc and based on that we see next results.
p.limit = 20

14.11 Whenever we search, then query always return all result and then based on p.limit it shows the data.
However we can restrict the behavior to searh all data and instruct to search top 10 or 20 etc results using another
predicate called p.guessTotal

       type = dam:Asset
       path = /content/dam/we-retail
       p.offset = 0
       p.limit = 20
       p.guessTotal = true -> will search only 20 results

Note - we can also assign p.guessTotal = 100, to search in top 100 results only.


14.12  
Search for property exists in AEM CQ5 JCR
If you want find out that a property exists in JCR, then use the query debug tool with the following query

type=nt:unstructured
path=/content/bt/business
property=erpDealerId
property.operation=exists
p.hits=full
p.limit=-1
type - Node type that you want to perform your search on.
path - Path you want to perform the search.
property - The property that you want to test if it exits.
property.operation - The operation you want to perform for the test. The available operations are equals, unequals, like, not, exists.
p.hits - Provides all the properties of the matched nodes.
p.limit - Limit your results.



15. ----Composite Components in AEM using "cq:template"------

15.1  Create a node (nt:unstructured) called cq:template under your component.
15.2  Create another node below cq:template, eg: container and sling:resourceType -> foundation/components/parsys
15.3  Create node that you want to be dropped by default eg : text and point it to respective resourceType.
15.4  Make sure the sightly of your component includes ->
<div data-sly-resource="${'container' @ resourceType='foundation/components/parsys'}" data-sly-unwrap>
Here name "container" should match with the node name created under cq:template node.

16. ----Event Handling---------

AEM provides 4 ways to handle events using below API's:

16.1 JCR API's ->  implement EventListener. Event handling done at JCR/Repository level and registered via ObservationManager (refer JCRHandler.java)

16.2 OSGi API's ->  implement EventHandler (refer OSGiEventHandler.java)
a. are registered with Framework service registry(i. like OSGi service) and are notified with an Event Object.
b. can inspect the received event object to determine its topic and properties.
c. event objects must be registered with a service property EventConstants.EVENT_TOPIC whose values are list of topics
for which the event handler listen to. 	  
d. can also be registered with EventConstants.EVENT_FILTER service property to further filte the events.


16.3 Sling API's -> implement ResourceChangeListener
a. are registered with Framework service registry(i. like OSGi service) and are notified with an ResourceChange Object.
b. can inspect the received ResourceChange objects to determine the type of change,location and other properties.
c. ResourceChange must be registered with a service property PATHS whose value is list of resource paths .
d. must be registered with service property CHANGES whose value is list of event type.

16.4 Sling Job Manager/Consumer -> JobManager and JobConsumer
a. gives guruantee of processing atleast once.
b. JobManager allows to create new jobs , search for jobs, and get staistics about the current state.
c. JobConsumer consumes a Job. It registers with the PROPERTY_TOPICS service registry property.
d. Steps to create ->
1. Create a Event Handler(using Sling/OSGi API) -> GeeksJobCreater.java
2. Inside the Handler, we will create a Custom Job using Job Manager i.e Job job=jobManager.addJob("geeks/job",jobProperties);
3. We create JobConsumer Service to consume this Job.(GeeksJobConsumer.java) This Job consumer returns one of 4 status name:
OK -> Process Finished Successfully
FAILED -> Proceesing Failed but might be retried(depending on the limit of retries set)
CANCEL -> Processing failed permanently and will not be retried.
ASYNC -> Processin Will Be Done Asynchronously.

NOTE - 1. If a Job is failed , then AEM stores the data under /var/eventing/jobs/cancelled with all relevant information.
2. AEM Sling Event Console http://localhost:4502/system/console/slingevent contains info like queue,retries etc.
3. We can edit retries , delay etc in config called - Apache Sling Job Default Queue



17.    ---Context Aware Configuration---

17.1 UseCase -> If we want the different configuration for different page hierarchy eg: us,in,de then we can make use of factory OSGi
configuration but if we want different config for subnodes as well under them eg: under us -> en and es and so on...In such cases
factory config will become complex to manage.
To overcome this, we make use of Context Aware Configuration.
17.2 CAC are stored under /conf/<project>. eg: /conf/sumit/us , /conf/sumit/in
17.3 We add property sling:configRef to page hierarchy in /content. The value of this property is equal to specific config that we want to load from /conf.
17.4 Context Aware configuration resolve from child to parent. For example
/conf/aemgeeks/us/en/sling:configs/GeeksCAConfig
/conf/aemgeeks/us/sling:configs/GeeksCAConfig
/conf/aemgeeks/sling:configs/GeeksCAConfig
If CA Config does not exist in /conf/aemgeeks/us/en/ then it will look at parent /conf/aemgeeks/us/
it keep looking to parents until it find configuration. Keep in mind, you have added sling:configRef property in content.
17.5 Steps to create context aware.
a. Create OSGI Config and Sling Model consuming the config -> CAConfigImpl.java
b. Under /conf/sumit/sling:configs/ create a node nt:unstructured -> com.aem.sumit.core.services.contextaware.GeeksCAConfig. Add the properties
that were defined in OSGI Service.
c. Under /content/sumit/jcr:content -> Add a property called sling:configRef -> assign the values as /conf/sumit. This will look for sling:configs
in /conf/sumit and assign the value to our component.

18.   --------Workflow----------

18.1 To ceate Workflow,Workflow Model needs to be created which contains series of steps.
Worflow model is stored in 2 places:
1. /conf/global/settings/workflow/models/geeks-model -> Model Design
2. /var/workflow/models/geeks-model -> Runtime Model (used when workflow is executing)

18.2 Different Steps:
1. Container Step -> When you want to execute another workflow(child workflow)
2. Participant Step -> When you want human interaction i.e assign some task.
3. Decision Step -> When you need branching (AND / OR)
4. Process Step -> Own custom process.

18.3 Common ways to execute workflow:
1. Launcher -> When you want to automate the trigger of the workflow eg: perform some task when page is created.
2. API/Code
3. Manually.



18.4 Launcher can be created with console - http://localhost:4502/libs/cq/workflow/admin/console/content/launchers.html.
If we have access to WorkflowModel and Payload , then we can trigger Workflow from backend (SlingModel,Servlet,Scheduler,Services)
Below are the steps to trigger:
1. Get WorkflowSession ->          WorkFlowSession session = resourceResolver.adaptTo(WorkflowSession.class)
2. Get WorkflowModel   ->          WorkflowModel model  = session.getModel("/var/...")
3. Get WorkFlowData(i.e payload)   WorkflowData data = session.newWorkflowData("JCR_PATH",payload);
4. Start workflow      ->          session.startWorkflow(model , data)

	  We can also pass metadata.         Map<String,Object> metaData = new HashMap();
	                                     session.startWorkflow(model , data, metaData);
										 
		http://localhost:4502/libs/cq/workflow/admin/console/content/models.html -> 
		Page Version For All Sumit Pages 
		1. This workflow creates a page version and is triggered via launcher which listens for cq:Page modified under /content/sumit/
        2. Launcher can be searched with globbing pattern /content/sumit/us	in Launchers Console.
        Page Version
        1. This workflow makes use of custom workflow process created below(i.e GeeksWorkflowProcess.java)	
        2. This workflow is triggered from servlet - SimpleWorkflow.java.
           http://localhost:4502/bin/executeworkflow?page=/content/sumit/us/en/adventures
           When we hit the servlet, the version will be created for page passed as query parameter and two properties(approver1 and approver2) 
		   will be added in jcr:content .		   

18.5 Custom Workflow Process - GeeksWorkflowProcess.java (This custom process step will add two properties(approver1 and approver2)
in the page that is passed as Payload and create a version)
1. This will be availabele inside dropdown of Process Step.
2. implements WorkflowProcess and overrides execute(workitem,workflowsession,metadatamap)
3. property -> process.label is important as it will be selected in process step.
4. We can get dialog properties by MetaDataMap using key ("PROCESS_ARGS")

	  NOTE -> Custom Workflow Process and Custom Workflow Step are two different things.
	          Custom Workflow Process -> is a backend module which can be selected when we use Process Step .
			                             It doesn't have its dialog and have to use dialog of Process Step.
              Custom Workflow Step -> To write custom workflow step, we need a component. This component will not have rendering script and backend logic.
                                      It will have dialog and editConfig node. When we link Custom Workflow Process (eg:GeeksWorkflowProcess ) to this
            						  component,then this setup is referred as Custom Workflow Step.
                                      This will be now available as a new step in workflow model, which we can drag and drop.									  

18.6 Complete flow for creating Custom Workflow Step.
1. Created custom workflow process -> GeeksWorkflowStep.java
2. Created component -> geeksworkflowstep. It has cq:dialog and cq:editConfig.
Under cq:editConfig -> cq:formParameters , important property is PROCESS whose value is path of our
Workflow Process created in Step1.
i.e  PROCESS="com.aem.sumit.core.workflow.GeeksWorkflowStep"
3. For the first time , this new Workflow Step will not be available to drag and drop in Workflow Model.
We need to copy the 'default' node from /libs/settings/wcm/designs/default	  to /apps/settings/wcm/designs/default
4. Under /apps/settings/wcm/designs/default/jcr:content/model/flow , we added our component group .
5. This new Custom Workflow Step can now be added by drag and drop and values can be configured in dialog.

     NOTE - Whatever property that we define in cq:dialog, the name should be prepended with /metaData
            eg: name -> ./metaData/BRAND	 


18.7 Refer Workflow Model - Geek Participant Step -> to understand Partipant Step.

18.8 Workflow Stages -> Whenever an approve approves/rejects a task assigned , he is not aware of the history of the workflow i.e how far it has reached.
With the help of workflow stages, we divide the entire workflow into logical sections and each section contains the info.
Refer workflow-stage.png for pictorial view.
1. To create stages/sections, edit the model and select Stages Tab and add the stages you want(eg: Create, Review, Approve)
2. Open the Workflow Step, and you will find the new drop-down where you can assign the stage.
3. Once stages are assigned. Approver can go to inbox and click on open -> Workflow Info .

18.9 Refer Worflow Model - Geek Publish Workflow -> approve/reject the page activation.


--------------------------------------------Dispatcher Setup----------------------------------------------
1. Download httpd-2.2.2-win32-x86-no_ssl (apache web server) and dispatcher module dispatcher-apache2.2-windows-x86

2. Double click ssl file and install and change the directory to C:\apache2\

3. Copy disp_apache2.2.dll to C:\apache2\modules.

4. In apache 2.2, starting point is httpd.conf located at C:\apache2\conf.

5. Open httpd.conf and add the module we added:
   LoadModule dispatcher_module modules/disp_apache2.2.dll

6. Copy IF block from httpd.conf.disp2 and paste in httpd.conf  . httpd.conf.disp2 file is located in dispathcer-apache2.2
   <IfModule disp_apache2.c> ....</IfModule disp_apache2.c>

7. The IF module tells about the configuration files of dispatcher.

8. Again copy the <Directory /> block to httpd.conf

9.The <Directory /> block has "SetHandler dispatcher-handler" which tells that all processing will be done by
our module that we loaded i.e disp_apache2.2.dll

10. Copy dipatcher.any to C:\apache2\conf
    Note: In httpd.conf, ensure that "ServerRoot "C:/apache2" always points to apache installation directory.

11. Restart the apache server. If everything is fine server will restart and log with name "dispatcher.log" will be
    generated at C:\apache2\logs

12. Change the cache root path in dispatcher.any under /cache farm.
    /docroot "C:/apache2/htdocs"



------------------- AEM Headless-GraphQl API ------------------------

1. GraphQL API's are used for delivery of Content Fragments.
   GraphQL is a query language and helps to get what we want.
   npm init -y
   npm express express-graphql nodemon graphql
   npm i --save-dev @babel/cli @babel/core @babel/node @babel/preset-env
   npm i lodash casual sqlite3 mongoose sequelize

graphql -> is the library.
graphiql -> is the UI for running graphql query.

Om Hamm Hanumate Namah
At risk of missing to mention someone who worked on this, apologies in advance

db.insertMany([{name:'learn-react',upvote:0,comment:[]},{name:'learn-aem',upvote:0,comment:[]},{name:'learn-node',upvote:0,comment:[]}])

---------------------------AMARR----------------------------
esd-onecms-dev -> used for Phase2
esd-onecms-qa -> used for Phase2
amarr-onecms-dev

onecms push the code once in 2 weeks.
We develop and include that in RC.
onecms creates a RC.
We merge our code in that RC.
Its deployed in int environment.(assa-abloy-onecms-int)
After a week code is pushed to assa-abloy-onecms-stage and assa-abloy-onecms-prod

Nagarro can only control build for esd-onecms-dev and esd-onecms-qa
AMS team controls amarr-onecms-dev.
We cannot trigger build from cloudmanager. In order to do, jenkins is setup and separate pipelines are created to deploy on esd-onecms-dev,
esd-onecms-qa and amarr-onecms-dev


2023/02/16 03:16:55 The following errors were found in the httpd config: /tmp/dispatcher/unzippedConfiguration/conf.d/enabled_vhosts/ecom.amarr.com.vhost: appears to be a windows (pseudo) symlink but this OS does not support these. Please commit a correct symlink: ../available_vhosts/ecom.amarr.com.vhost
2023/02/16 03:16:55 Dispatcher configuration validation failed:


Use the following commands on Git Bash:
export MSYS=winsymlinks:nativestrict
ln -sfv ../available_vhosts/assaabloy.com.vhost assaabloy.com.vhost
Update the file names accordingly
To create symlinks then it should work



dev2.amarr.com - for the dealers on amarr.com
dev2-atotaldoor.com - for the dealer-microsite i.e, homeowner flow
for sign in popup on these sites
username - amarr
password - Amarrebusiness@123


test.atdcomp@mailinator.com
Dealer810258

46015
66051

Jenkins- Sumit-yadav
Ed3zpvJ5zDjNec48

esdMergeAmarr2 -> checkout develop_esd,deveop_sdc
take pull from develop_ amarr2
Resolve any errors/conflicts
Create PR from esdMergeAmarr2 -> develop_esd
Use this for ref- https://github.com/ASSAABLOY/esd-global-web/pull/1082
Login to Jenkins:
https://jenkins.globalweb.aws.assaabloy.net/
Open- AEM Cloud Deploy QA ESD to deploy on QA and DEV both
Open- AEM Cloud Deploy DEV ESD to deploy on DEV only
Click on Build Now
Under Initialization - Select develop_esd and proceed
Check the status of build -
https://experience.adobe.com/#/@assaabloy/cloud-manager/activity.html/program/14557


adminnew
adm1n@2023
https://development-q5nzhaa-yygpweufxbtek.us-a1.magentosite.cloud/adm1n2021


https://qa2-atotaldoor.amarr.com/us/en/garage-doors/explore-products/garage-door-openers-and-accessories?accessoryType=Light&accessoryType=Remote&accessoryType=Battery%20Backup&accessoryType=Keypad&accessoryType=Laser%20Parking&accessoryType=Smart%20Camera&accessoryType=Smart%20Control&accessoryType=Smart%20Light&accessoryType=Smart%20Lock&accessoryType=Smart%20light%20control&accessoryType=Wall%20Station
https://dealer.amarr.com/us/en/garage-doors/explore-products/garage-door-openers-and-accessories?accessoryType=Light&accessoryType=Remote&accessoryType=Battery%20Backup&accessoryType=Keypad&accessoryType=Laser%20Parking&accessoryType=Smart%20Camera&accessoryType=Smart%20Control&accessoryType=Smart%20Light&accessoryType=Smart%20Lock&accessoryType=Smart%20light%20control&accessoryType=Wall%20Station


Under secondary Navigation - the position of Accessories Price is not per figma
The Column Heading should be "Consumer Starting Price" and NOT "Consumer Final Price"
The pop up on click on save button does not look meaningful.

https://qa2-atotaldoor.amarr.com/us/en/garage-doors/explore-products/view-all-doors/lm-380ut-univ-2-button-remote?price=IjIwMC42NyI=&diy=false&isaccessory=true

accessoryRequireOpenerOrDoor : "Accessories cannot be purchased online by itself. Accessory purchase requires a garage door or opener purchase."
okButton : "OK"

shopcart
https://qa2.amarr.com/us/en/dealer/my-account/e-commerce/orders/orders/edit-order-details/84000000949













10 ways u can use ChatGPT to 10x your productivity as a Developer and DevOps/Cloud Engineer:

Explain code: Reading other people's code is hard. Even reading your own code from 2 months ago is hard. Ask ChatGPT to explain it, and it cuts the time in half.

Improve code: You know what you want to improve in your code. Ask ChatGPT how to improve it. You'll get an explanation, instructions, and a new version of the code.

Rewrite code using the right style: Someone used a different naming convention? ChatGPT can rewrite that code for you, with your style and preferences.

Rewrite code using idiomatic constructs: Once you've gone through several programming languages, you can pick one up in a week or two, and write working code. But that doesn't mean you're able to fully utilize the language's constructs to your advantage. Use ChatGPT to improve your code to take full advantage of the language. And if you're not sure what's going on, ask ChatGPT to explain the changes.

Simplify code: Ask ChatGPT to simplify complex code. The result will be a much more compact version of the original code. Ask it to optimize for readability, and it will give you an easier to read version.

Write test cases: Give it some code, and ask it for test cases. Or give it some requirements and ask it for test cases before you even write the code.

Explore alternatives: There's often multiple ways to solve a problem. Ask ChatGPT to explain the alternatives, so you can make an informed choice.

Write comments: Code should be self explanatory. But if you're writing a tutorial, an example, or anything for beginners, you want to add comments on every step. ChatGPT can do this in seconds.

Write documentation: We all hate documenting. Ask ChatGPT to do it, you'll get a great starting point.

Track down bugs: You know there's a bug in there. Ask ChatGPT to find it for you.

Keep in mind, ChatGPT makes mistakes. Don't trust it blindly (just like you'd never blindly trust any code from the internet).

As a developer, that's your new job: Using ChatGPT for the simple tasks, reviewing and improving its output, and concentrating on the things that it can't do (yet):

Talking to other people (except sending a mail)

Figuring out requirements

Architecting a solution

Putting everything together

--------------------------------------------------------------------------------------------------



Bluewhale10!



accessoryType=Light&accessoryType=Remote&accessoryType=Battery%20Backup&accessoryType=Keypad&accessoryType=Laser%20Parking&accessoryType=Smart%20Camera&accessoryType=Smart%20Control&accessoryType=Smart%20Light&accessoryType=Smart%20Lock&accessoryType=Smart%20light%20control&accessoryType=Wall%20Station

accessoryType=Light&accessoryType=Remote&accessoryType=Battery%20Backup&accessoryType=Keypad&accessoryType=Laser%20Parking&accessoryType=Smart%20Camera&accessoryType=Smart%20Control&accessoryType=Smart%20Light&accessoryType=Smart%20Lock&accessoryType=Smart%20light%20control&accessoryType=Wall%20Station


https://dealer.amarr.com/us/en/garage-doors/explore-products/garage-door-openers-and-accessories?accessoryType=Light&accessoryType=Remote&accessoryType=Battery%20Backup&accessoryType=Keypad&accessoryType=Laser%20Parking&accessoryType=Smart%20Camera&accessoryType=Smart%20Control&accessoryType=Smart%20Light&accessoryType=Smart%20Lock&accessoryType=Smart%20light%20control&accessoryType=Wall%20Station

https://qa2-atotaldoor.amarr.com/us/en/garage-doors/explore-products/garage-door-openers-and-accessories?accessoryType=Remote&accessoryType=Keypad

"amarr.PaymentDetails.paymentType": "DEBIT/CREDIT CARD",
"amarr.PaymentDetails.payNowButton": "PAY NOW"




        RewriteCond %{REQUEST_URI} ^/(.*)/\.
        RewriteRule ^.*$ - [R=404,L]
		
		This Apache Dispatcher rule blocks URLs that contain a trailing slash followed by a dot (.) in the path. The rule uses mod_rewrite to match the request URI against a regular expression pattern and rewrite the request to return a 404 error response. Here's how the rule works:

RewriteCond %{REQUEST_URI} ^/(.*)/.: This condition matches any request URI that contains a slash (/) followed by a dot (.) character immediately after the slash. The pattern uses parentheses to capture any characters between the leading slash and the trailing slash/dot, which can be referred to as $1 in the RewriteRule.
RewriteRule ^.*$ - [R=404,L]: This rule is applied if the RewriteCond pattern matches. It rewrites the request to a 404 response with no redirect by setting the replacement URL to a hyphen (-). The [R=404] flag specifies the HTTP status code to be 404, and the [L] flag indicates that no further rules should be processed.



RewriteCond %{REQUEST_URI} \.BAC [NC]


RewriteRule ^.*$ - [R=404,L]

The above code is a directive in Apache's mod_rewrite module that checks if the requested URI (Uniform Resource Identifier) ends with ".BAC" in a case-insensitive manner. If the condition is met, it responds with a 404 error code and stops further processing of rules.

RewriteCond %{REQUEST_URI} \.BAC [NC]
This line sets a condition for the rule to apply. In this case, it checks if the requested URI (stored in the %{REQUEST_URI} variable) contains the string ".BAC". The [NC] flag makes the match case-insensitive, so it would also match ".bac" or ".Bac".

RewriteRule ^.*$ - [R=404,L]
This line is the actual rewrite rule. The "^.*$" pattern matches any URI, and the "-" replacement means no substitution is made. The [R=404] flag sets the HTTP response code to 404, and the [L] flag stops processing of further rules.

If you want to rewrite the code in a different way, you could use a simpler regex pattern that checks only for the ".BAC" string at the end of the URI:
RewriteRule \.BAC$ - [R=404,L]


This rule would have the same effect as the original one but with a more concise pattern.


---------------------------------------------------------------------------------------------VHOST
A VHost file (short for virtual host file) is a configuration file that defines the virtual host configuration for an Apache HTTP Server instance.
Virtual hosts are used to serve multiple websites or applications from a single server, using a single IP address and port number.
Each virtual host is associated with a domain name or IP address, and can have its own configuration settings, such as document root,
error log file, and SSL certificate.

The VHost file is typically located in the conf directory of the Apache HTTP Server installation, under a subdirectory named extra
(on macOS and Linux) or conf.d (on some Linux distributions). The file name usually has a .conf extension, and contains a set of
configuration directives that define the virtual host settings.

Some common directives that are used in VHost files include:

ServerName and ServerAlias: specify the domain name or IP address associated with the virtual host
DocumentRoot: specifies the directory where the files for the virtual host are located
ErrorLog and CustomLog: specify the location of the error and access log files for the virtual host
Directory and Location: define access controls and settings for specific directories or URL paths within the virtual host
SSL and Proxy: define settings for SSL encryption and reverse proxying
By using virtual hosts, you can serve multiple websites or applications from a single server,
which can be more cost-effective and efficient than using separate servers for each site or application.
The VHost file provides a flexible and powerful way to configure the virtual hosts for an Apache HTTP Server instance,
and allows you to customize the behavior and settings of each virtual host to meet your specific needs.

------------------------------------------------------------------------------------How dispatcher.any is linked to vhost

The dispatcher.any file is linked to the VHost file through the VirtualHost directive in the dispatcher.any file.
The VirtualHost directive tells the AEM Dispatcher which virtual hosts to serve content for, and provides the path to the corresponding VHost file.

The syntax of the VirtualHost directive is as follows:
VirtualHost {
HostName "<hostname>"
DocumentRoot "<path-to-document-root>"
ErrorLog "<path-to-error-log>"
CustomLog "<path-to-access-log>" common
/ {
...
}
}

In this directive, <hostname> is the domain name or IP address of the virtual host, <path-to-document-root> is the path to the document
root directory for the virtual host, and <path-to-error-log> and <path-to-access-log> are the paths to the error and access log files for
the virtual host.

The / section specifies the configuration for the root URL of the virtual host, and can include additional directives such as RewriteRule,
RewriteCond, Header, and ExpiresByType, which are used to customize the behavior of the AEM Dispatcher for requests to the virtual host.

Once the VirtualHost directive is defined in the dispatcher.any file, the AEM Dispatcher reads the VHost file specified in the DocumentRoot
directive, and uses the virtual host configuration defined in the VHost file to serve content for the virtual host.
This allows you to manage the virtual host configuration separately from the AEM Dispatcher configuration, and provides a flexible
and scalable way to serve content for multiple virtual hosts from a single AEM instance.

------------------------------------------------------------------what loads first, dispatcher.any or Vhost

In the context of the AEM Dispatcher, the dispatcher.any file loads first, followed by the VHost file.

When the AEM Dispatcher is started, it reads the dispatcher.any file and initializes its settings and configuration.
This includes setting up caching rules, defining allowed and denied URLs, configuring load balancing and failover,
and specifying the virtual hosts that the Dispatcher will handle requests for.

Once the dispatcher.any file has been processed, the AEM Dispatcher reads the VHost file(s) specified in the VirtualHost directive(s)
in the dispatcher.any file. The VHost file contains the configuration settings for the virtual host, such as the document root directory,
error log file location, and access log file location. The AEM Dispatcher then uses this information to handle requests for the specified
virtual host.

So, to summarize, dispatcher.any file is loaded first and VHost file is loaded afterwards.


--------------------------------------------------------------VHOST def
1. <IfModule mod_headers.c>
   Header add X-Vhost "amarrcom"
   </IfModule>

The configuration snippet you provided is using the mod_headers module in Apache Web Server to add a custom HTTP header to the response of a web server.

The Header directive is used to add a header to the HTTP response. In this case, the directive Header add X-Vhost "amarrcom" adds a header with the name X-Vhost and the value amarrcom to the HTTP response.

The IfModule directive is used to ensure that the configuration is only executed if the mod_headers module is loaded in Apache. If the module is not loaded, the configuration is ignored.

Overall, this configuration snippet can be used to add a custom HTTP header to the response of a web server, which can be used for various purposes such as debugging, tracking, or security. In this particular example, the header is being used to identify the virtual host or website being served by the web server.

2. Options FollowSymLinks Includes

Options FollowSymLinks Includes is an Apache Web Server configuration directive that sets options for a directory or a virtual host.

The FollowSymLinks option allows Apache to follow symbolic links when accessing files in a directory. If this option is not set, Apache will not follow symbolic links and will return a 403 Forbidden error if a request is made for a file that is accessible only through a symbolic link.

The Includes option enables the use of server-side includes (SSI) in HTML files. SSI is a server-side scripting language that allows for dynamic content to be included in HTML files. For example, an HTML file can include the current date and time, or a header or footer that is shared across multiple pages.

It is important to note that enabling SSI can have security implications, as it allows for server-side scripting in the HTML files. Therefore, it is recommended to use SSI with caution and to ensure that proper security measures are in place.

In summary, Options FollowSymLinks Includes is an Apache Web Server configuration directive that sets options for a directory or virtual host. FollowSymLinks allows Apache to follow symbolic links, while Includes enables the use of server-side includes in HTML files.

3. AddOutputFilter INCLUDES .html

In summary, AddOutputFilter INCLUDES .html is an Apache Web Server configuration directive that enables the server-side includes filter for HTML files. It allows for dynamic content to be included in the HTML files and should be used with caution due to security implications.

4.Header set Surrogate-Control "private, max-age=0, stale-if-error=0, stale-while-revalidate=0"

Header set Surrogate-Control "private, max-age=0, stale-if-error=0, stale-while-revalidate=0" is an Apache Web Server configuration directive that sets the Surrogate-Control header for HTTP responses.

The Surrogate-Control header is used by caching proxies, such as a Content Delivery Network (CDN), to control how they cache the content. In this case, the private directive indicates that the response is intended only for a single user and should not be cached by shared caches, such as a CDN. The max-age=0 directive indicates that the response should not be served from cache and should always be fetched from the origin server. The stale-if-error=0 and stale-while-revalidate=0 directives indicate that cached content should not be used if the origin server is not available.

Overall, this directive is used to prevent caching of responses by shared caches, such as a CDN, and to ensure that clients always receive the most up-to-date content from the origin server. This can be useful in situations where the content changes frequently, or where it is important that clients always receive the latest information.

It is important to note that this directive may not be appropriate for all types of content, and that caching strategies should be carefully considered based on the specific requirements of the application or website.

In summary, Header set Surrogate-Control "private, max-age=0, stale-if-error=0, stale-while-revalidate=0" is an Apache Web Server configuration directive that sets the Surrogate-Control header for HTTP responses. It is used to prevent caching of responses by shared caches, such as a CDN, and to ensure that clients always receive the most up-to-date content from the origin server.

5. <LocationMatch "^/content/amarr/com/us/en/home/dealer/my-account.*">
   Header unset Cache-Control
   Header unset Expires
   Header unset Pragma
   Header always set Pragma "no-cache"
   Header always set Cache-Control "max-age=0, no-cache, no-store, must-revalidate"
   Header always set Expires "Thu, 1 Jan 1970 00:00:00 GMT"
   Header set Age 0
   </LocationMatch>
   This is an Apache Web Server configuration block that applies to requests matching the regular expression ^/content/amarr/com/us/en/home/dealer/my-account.*. The block contains a series of Header directives that control the caching behavior of responses for those requests.

The Header unset directives are used to remove any existing Cache-Control, Expires, and Pragma headers from the response, if they are present. These headers are typically used to control caching behavior, so by removing them, the server ensures that the caching behavior is explicitly set by the subsequent Header always set directives.

The Header always set directives are used to add new Pragma, Cache-Control, and Expires headers to the response, with specific values that control caching behavior. In this case, the headers are set to disable caching entirely, by setting the max-age, no-cache, no-store, and must-revalidate cache control directives, and setting the Expires header to a date in the past (January 1, 1970). The Pragma header is also set to no-cache, to ensure that older clients that do not understand the Cache-Control header also disable caching.

The Header set Age 0 directive is used to set the Age header to 0, which indicates that the response is not stale and should not be served from a cache.

Overall, this configuration block is used to ensure that responses for the specified requests are not cached by the client or any intermediary caches, such as a CDN. This can be useful for pages that contain sensitive or frequently-changing information, such as account information or shopping cart contents, where caching could lead to stale or incorrect information being displayed to the user.

6. <LocationMatch "\.(?i:pdf)$">
   ForceType application/pdf
   Header set Content-Disposition inline
   </LocationMatch>

   This Apache configuration block uses a regular expression to match any requests for files with a ".pdf" extension, regardless of whether the extension is uppercase or lowercase, and applies two directives to those requests.

The ForceType directive is used to set the Content-Type header of the response to application/pdf, which indicates that the response contains a PDF file. This ensures that the browser and any other user agents handling the response know how to display or download the file.

The Header set directive is used to set the Content-Disposition header to inline, which indicates that the response should be displayed "inline" in the browser, rather than being downloaded as an attachment. This directive is often used for files that can be displayed directly in the browser, such as PDFs, images, and videos.

Overall, this configuration block is used to ensure that PDF files are served with the correct Content-Type header, and that they are displayed inline in the browser rather than downloaded as attachments, which can improve the user experience for users who want to view PDFs without downloading them.

7. <Location ~ ".*.lc-.*-lc.(min.)?(js|css)$">
   # Important - Cache control is set to immutable by AEM
   Header unset Cache-Control
   Header always set Cache-Control: max-age=2592000
   </Location>

   This Apache configuration block uses a regular expression to match any requests for resources that have a specific naming convention. The naming convention matches any resources that contain .lc-, followed by any character, then -lc., and optionally min. followed by either js or css.

For example, this pattern would match resources like:

example.lc-en-us-lc.min.js
styles.lc-fr-ca-lc.css
Once the request for a matching resource is received, this configuration block modifies the Cache-Control header for the response by unsetting the existing value and then setting a new value. The existing Cache-Control header is unset to ensure that any previous configuration is overridden.

The new Cache-Control header value is set using the Header always set directive. The value is max-age=2592000, which indicates that the resource should be cached by the browser and intermediate caches for 30 days (2592000 seconds).

The always keyword ensures that the Cache-Control header is set for all responses that match the regular expression, regardless of the value of the Cache-Control header in the original response.
Overall, this configuration block is used to set a long-term caching policy for resources with a specific naming convention, which can improve performance and reduce server load by reducing the number of requests for those resources.

8. SetEnvIfNoCase Request_URI "^/(us)/(en)" MARKET=$1
   SetEnvIfNoCase Request_URI "^/(us)/(en)" LANGUAGE=$2

The SetEnvIfNoCase directive is used in Apache configuration files to set environment variables based on the contents of the request.

In this example, there are two SetEnvIfNoCase directives that set the MARKET and LANGUAGE environment variables based on the contents of the Request_URI variable.

The first directive sets the MARKET environment variable to "us" if the Request_URI contains "/us/en". The second directive sets the LANGUAGE environment variable to "en" if the Request_URI contains "/us/en".

The ^ character at the beginning of the regular expression indicates that the pattern should match at the start of the Request_URI. The parentheses are used to capture the matched groups and the $1 and $2 variables are used to reference the captured values in the environment variable assignment.

The SetEnvIfNoCase directive is typically used to set environment variables that can be used by other Apache directives or scripts running on the server.

9.      RewriteEngine on
    	RewriteMap lowercase int:tolower
   	
   	The RewriteEngine directive enables the use of the Apache URL rewriting module, which allows you to modify URLs dynamically based on various criteria.

The RewriteMap directive defines a mapping between a set of input values and a set of output values. In this example, the lowercase map is defined as a case-insensitive mapping between input strings and their lowercase equivalents. The int:tolower option specifies that the mapping should be done using the tolower function, which converts all characters in the input string to lowercase.

Once the RewriteEngine is enabled and the RewriteMap is defined, you can use rewrite rules to modify URLs using the mapped values. For example:


RewriteRule ^/path/(.*)$ /path/${lowercase:$1} [L]
This rule matches requests for URLs that start with "/path/", captures the remainder of the URL as a group, and then rewrites the URL to use the lowercase equivalent of the captured group using the lowercase map. The [L] flag indicates that this is the last rule that should be processed for this request.

Overall, this combination of directives enables you to perform dynamic URL rewriting based on various criteria, such as the requested URL, the request headers, or other factors.

eg: www.myurl/path/SUMIT/Yadav

The final output of the URL after applying the RewriteRule will be:
www.myurl/path/sumit/yadav

Here, the original URL was www.myurl/path/SUMIT/Yadav, and after applying the RewriteRule, the first part of the URL after /path/, which is SUMIT/Yadav, is converted to lowercase by the RewriteMap and the final URL becomes www.myurl/path/sumit/yadav.

10. RewriteCond %{REQUEST_URI} !^/$
    RewriteRule ^(.*)/+$ https://%{HTTP_HOST}$1 [R=301,L]

    This set of directives applies a 301 redirect to remove trailing slashes from URLs.

Explanation of each line:

RewriteCond %{REQUEST_URI} !^/$: This line checks if the requested URI does not already end with a slash. The exclamation mark before the pattern means "not".
RewriteRule ^(.*)/+$ https://%{HTTP_HOST}$1 [R=301,L]: This line matches any URL that ends with one or more slashes and captures everything before the slash in a backreference. The rule then replaces the match with the captured content and redirects to the same URL without the trailing slash. The [R=301,L] flag indicates a permanent redirect (301) and that this is the last rule to be applied.
For example, if the user requests the URL https://example.com/my-page/, the rule will redirect them to https://example.com/my-page.

11. RewriteRule ^(.*)/+$ https://%{HTTP_HOST}$1 [R=301,L]
    This RewriteRule is used to redirect URLs with trailing slashes to the same URL without the trailing slash, using HTTPS protocol. Here is an explanation of the individual parts of the rule:

RewriteRule: starts the definition of a rewriting rule
^(.*)/+: matches any URL that ends with one or more forward slashes, and captures the part before the slashes in a backreference ($1)
https://%{HTTP_HOST}$1: is the replacement string, which will replace the matched part of the URL (i.e., the trailing slash) with the original URL minus the trailing slash, but with the HTTPS protocol and the same domain name (%{HTTP_HOST})
[R=301,L]: specifies that this is a permanent redirect (status code 301), and that this is the last rule to be executed for the current request (L)
So for example, if someone requests the URL http://example.com/path/with/trailing/slash/, the rule will redirect them to https://example.com/path/with/trailing/slash, without the trailing slash.

12. RewriteCond %{REQUEST_URI} ^/(.*)/\.
    RewriteRule ^.*$ - [R=404,L]

    The above RewriteCond and RewriteRule combination is used to return a 404 error for any request that ends with a forward slash followed by a dot.

For example, if someone tries to access "www.example.com/some/directory/." (note the dot at the end), the server will return a 404 error.

Here's how the rule works:

The RewriteCond checks if the request URI ends with a forward slash followed by a dot. The pattern used in the condition is ^/(.*)/\.. The ^ character matches the start of the string, (.*) matches any character zero or more times (captured in $1), /\. matches a forward slash followed by a dot.
If the condition is true, the RewriteRule is applied. The rule is ^.*$ - [R=404,L]. The pattern ^.*$ matches any string, but since the condition has already filtered out requests with a forward slash followed by a dot, the rule will only be applied to other requests. The replacement - means no substitution is performed. The flags [R=404,L] indicate that a 404 error should be returned (R=404) and the processing should stop (L).

13. RewriteCond %{REQUEST_URI} !^/(apps|bin|content|etc|home|libs|system|tmp|var|saml_login|api)
    RewriteRule ^/$  https://%{HTTP_HOST}/%{ENV:MARKET}/%{ENV:LANGUAGE} [R=301,L]

    This rewrite rule checks if the requested URI does not start with any of the listed directory names (apps, bin, content, etc.), and if the requested URI is the root directory (i.e., "/"). If both conditions are true, it redirects to the HTTPS version of the same host, and appends the environment variables MARKET and LANGUAGE to the URL path. The resulting URL would be in the format of "https://<hostname>/<MARKET>/<LANGUAGE>". The [R=301,L] flags specify that the redirection should be a permanent (301) redirect and it should be the last (L) rule to be applied.



	-----------------------------------
	 map.put("type", "nt:unstructured");
        map.put("1_property", "sling:resourceType");
        map.put("1_property.value", "amarr/components/content/webcomponents/productDetails/v1/productDetails");
        map.put("2_property","productType");
        map.put("2_property.value", "openers");
        map.put("p.limit","-1");
	
	
	
	To create an index for the above query, you need to identify the properties used in the query and create an index for them in the AEM instance.

Based on the query provided, the following properties need to be indexed:

path
sling:resourceType
productType
To create an index for these properties, you can follow these steps:

Open the CRXDE Lite console by navigating to http://localhost:4502/crx/de/index.jsp in your web browser.

Navigate to the /oak:index node in the CRXDE Lite console.

Create a new index node by right-clicking on the /oak:index node and selecting "Create" > "Index".

Set the name of the new index to something meaningful, like "productIndex".

Add the properties to the index by adding the following properties to the index node:

jcr:primaryType = oak:QueryIndexDefinition
compatVersion = 2
type = property
propertyNames = ["path", "sling:resourceType", "productType"]
Save the changes to the index node.

Wait for the index to be built by the AEM instance. This may take some time depending on the size of your repository.

Once the index has been built, you can run the query provided in your code, and it should use the newly created index to improve performance.

--------------------------------------------------Deployment Pattern AEM------------------------------
1. Single Server Deployment: This deployment pattern involves deploying AEM on a single server. It is suitable for small businesses or projects with low traffic.

2. Author and Publish Deployment: In this deployment pattern, AEM is deployed on two servers: an author instance for content creation and management, and a publish instance for content delivery. This pattern is suitable for larger businesses or projects with high traffic.

3. Clustered Deployment: This pattern involves deploying AEM on multiple servers to provide load balancing and failover capabilities. It is suitable for businesses that require high availability and performance.


Clustered deployment in Adobe Experience Manager (AEM) involves deploying multiple AEM instances on different servers, with each instance working together to provide load balancing, high availability, and failover capabilities.

In a clustered deployment, there are typically two types of instances:

Author instances: These instances are used for content creation, editing, and management. Multiple author instances can be deployed in a cluster to provide load balancing and high availability.

Publish instances: These instances are used for content delivery to end-users. Multiple publish instances can be deployed in a cluster to provide load balancing and high availability.

To ensure that all instances in the cluster are synchronized, a shared repository is used. The shared repository is typically hosted on a separate server and contains all the content, assets, and configurations that are used by the author and publish instances.

In a clustered deployment, load balancing is achieved by distributing requests across multiple instances in the cluster. If one instance fails, the load balancer can automatically redirect requests to other available instances in the cluster, ensuring high availability and minimizing downtime.

Clustered deployments in AEM require careful planning and configuration to ensure that all instances are properly synchronized, and that content and configuration changes are propagated across the cluster in a timely manner. AEM provides tools and documentation to help with the setup and maintenance of clustered deployments.


4. Cloud Deployment: AEM can be deployed on cloud-based platforms like Amazon Web Services (AWS) or Microsoft Azure. This deployment pattern provides scalability   and flexibility, as resources can be added or removed as needed.

5. Hybrid Deployment: This pattern combines on-premise and cloud-based deployments of AEM. It allows businesses to take advantage of the benefits of both deployment models.


----------------------------------STORAGE MECHANISM-------------

Adobe Experience Manager (AEM) uses several storage mechanisms to store different types of content and data. Here are some of the most common storage mechanisms used in AEM:

1. Apache Jackrabbit Oak: This is the default storage mechanism used by AEM to store content and configuration data. Oak is a hierarchical document store that uses the Java Content Repository (JCR) API to store and retrieve data.

Apache Jackrabbit Oak, the default content repository implementation used in Adobe Experience Manager (AEM), provides two storage mechanisms: TarMK and MongoMK.

TarMK (Tar File-based Microkernel) is a file-based storage mechanism that uses a tar file to store the content repository data. It is the default storage mechanism for AEM and is well-suited for smaller repositories with a few million nodes. TarMK is a fully functional in-memory database that provides high-performance read and write operations. TarMK is also suitable for deployments that require frequent content updates, as it allows for efficient incremental backups and versioning of content.

MongoMK (MongoDB Microkernel) is a storage mechanism that uses MongoDB as a back-end data store. It is designed for larger repositories with millions of nodes and provides high scalability and performance. MongoMK is optimized for read-heavy workloads and provides fast query performance through its index-based search capabilities. MongoMK also provides support for distributed deployment, allowing for high availability and disaster recovery.

The choice between TarMK and MongoMK as the storage mechanism for AEM depends on the specific requirements and constraints of the deployment. Some factors that may influence the choice include the size of the repository, the expected traffic volume, the frequency of content updates, and the required scalability and high availability. AEM provides tools and documentation to help with the configuration and maintenance of both TarMK and MongoMK.



2. File system storage: AEM can store binary files such as images and videos on the file system, rather than in the content repository. This can help to improve performance by reducing the load on the content repository.

3. MongoDB: AEM can use MongoDB as a data store for content and configuration data. MongoDB is a NoSQL database that provides high scalability and performance.

4. Amazon S3: AEM can store binary files on Amazon S3, a cloud-based object storage service. This can help to reduce storage costs and improve scalability.

5. Shared file system: In a clustered deployment, AEM instances can share a file system to store content and configuration data. This can help to ensure that all instances have access to the same data and can operate as a single system.

Each storage mechanism has its own benefits and challenges, and the choice of mechanism depends on the specific business requirements and constraints. AEM provides tools and documentation to help with the configuration and maintenance of storage mechanisms.


---------------------------AEMAACS New Features---

AEMAACS (Adobe Experience Manager as a Cloud Service) is a cloud-native version of Adobe Experience Manager (AEM) that offers several new features and improvements over the on-premises version of AEM. Here are some of the new features and improvements in AEMAACS:

Continuous delivery: AEMAACS provides continuous delivery of updates and new features, ensuring that customers always have access to the latest version of AEM. This eliminates the need for manual upgrades and reduces downtime.

Elastic scalability: AEMAACS can automatically scale up or down based on demand, ensuring that resources are only allocated when needed. This can help to reduce costs and improve performance.

Cloud-native architecture: AEMAACS is designed to run natively on cloud platforms such as Amazon Web Services (AWS) and Microsoft Azure. This architecture provides improved scalability, high availability, and disaster recovery capabilities.

Improved authoring experience: AEMAACS provides an improved authoring experience with a modern, intuitive interface and improved performance.

Improved content delivery: AEMAACS provides improved content delivery through the use of a global content delivery network (CDN), which helps to reduce latency and improve performance for users around the world.

Improved security: AEMAACS provides improved security through a range of features, including automatic security updates, encryption at rest and in transit, and role-based access control.

Improved integration with other Adobe products: AEMAACS provides improved integration with other Adobe products, including Adobe Creative Cloud and Adobe Marketing Cloud, enabling a seamless workflow for digital marketers and content creators.

These are just some of the new features and improvements in AEMAACS. As a cloud-native version of AEM, AEMAACS offers several benefits over the on-premises version of AEM, including improved scalability, high availability, and faster time-to-market for new features and updates.

-----------------------------Adobe Cloud Manager

Adobe Cloud Manager is a cloud-based solution that helps organizations to manage their Adobe Experience Cloud implementations. It provides a unified interface for managing the deployment, testing, and release of Adobe Experience Cloud applications, including Adobe Experience Manager, Adobe Target, Adobe Analytics, and Adobe Campaign.

Here are some of the key features of Adobe Cloud Manager:

Continuous integration and delivery: Adobe Cloud Manager provides a fully automated continuous integration and delivery (CI/CD) pipeline, allowing organizations to quickly and easily deploy new features and updates to their Adobe Experience Cloud applications.

Release management: Adobe Cloud Manager provides a unified release management process for all Adobe Experience Cloud applications, allowing organizations to easily manage multiple releases across different environments.

Collaboration: Adobe Cloud Manager provides collaboration features, such as shared workspaces and task tracking, that allow teams to work together more effectively.

Environment management: Adobe Cloud Manager provides a simple and streamlined interface for managing environments, allowing organizations to easily create and manage development, staging, and production environments.

Automated testing: Adobe Cloud Manager includes automated testing capabilities, allowing organizations to easily test their Adobe Experience Cloud applications and ensure that they are functioning correctly before release.

Security and compliance: Adobe Cloud Manager is designed to meet the most stringent security and compliance requirements, with built-in security features such as encryption and access control.

Overall, Adobe Cloud Manager helps organizations to streamline their Adobe Experience Cloud implementation process and ensure that their applications are deployed, tested, and released quickly and efficiently.

----------------------------AEMAACS vs Adobe Cloud Manager---------

AEMAACS (Adobe Experience Manager as a Cloud Service) and Adobe Cloud Manager are both cloud-based solutions provided by Adobe for managing Adobe Experience Cloud implementations. However, they have different focuses and serve different purposes.

AEMAACS is a cloud-native version of Adobe Experience Manager that provides a fully managed and scalable solution for creating and managing digital experiences. AEMAACS offers several benefits over the on-premises version of AEM, including improved scalability, high availability, and faster time-to-market for new features and updates. AEMAACS also provides a more modern authoring experience, with improved performance and a simplified user interface.

Adobe Cloud Manager, on the other hand, is a cloud-based solution that helps organizations to manage the deployment, testing, and release of Adobe Experience Cloud applications. Adobe Cloud Manager provides a unified interface for managing the release management process of Adobe Experience Cloud applications, including Adobe Experience Manager, Adobe Target, Adobe Analytics, and Adobe Campaign. Adobe Cloud Manager provides a fully automated continuous integration and delivery (CI/CD) pipeline, allowing organizations to quickly and easily deploy new features and updates to their Adobe Experience Cloud applications.

In summary, AEMAACS is a fully managed and scalable solution for creating and managing digital experiences, while Adobe Cloud Manager is a cloud-based solution for managing the deployment, testing, and release of Adobe Experience Cloud applications. AEMAACS provides a more modern and simplified user interface, while Adobe Cloud Manager provides a fully automated continuous integration and delivery (CI/CD) pipeline for releasing new features and updates to Adobe Experience Cloud applications.


---------Moving from an on-premise installation to AEMAACS-----------------


Moving from an on-premise installation of Adobe Experience Manager (AEM) to AEM as a Cloud Service (AEMAACS) requires careful planning and execution to ensure a smooth transition. Here are some key steps to consider:

Analyze your existing AEM implementation: Before migrating to AEMAACS, it is important to analyze your existing AEM implementation to identify any customizations or integrations that may be affected by the migration. This analysis can help you determine the scope of the migration and any potential risks or challenges.

Develop a migration plan: Based on your analysis, develop a detailed migration plan that outlines the steps involved in moving from your on-premise AEM installation to AEMAACS. This plan should include timelines, resource requirements, and contingency plans for managing any issues that may arise during the migration.

Assess your content and assets: As part of the migration process, you will need to assess your content and assets to determine what needs to be migrated to AEMAACS. This assessment can help you identify any content or assets that may require special handling or migration processes.

Configure AEMAACS: Once you have completed the analysis and planning stages, you can begin configuring AEMAACS to meet your requirements. This may involve configuring security settings, workflows, and other features to meet your organization's needs.

Migrate your content and assets: After you have configured AEMAACS, you can begin migrating your content and assets to the new environment. This may involve exporting content from your on-premise AEM installation and importing it into AEMAACS.

Test and validate: After you have migrated your content and assets, it is important to test and validate your new AEMAACS environment to ensure that everything is working as expected. This may involve running user acceptance testing (UAT) to ensure that your users can access and interact with the new environment.

Go live: Once you have completed testing and validation, you can go live with your new AEMAACS environment. This may involve migrating your DNS records and configuring any necessary redirects to ensure that your users can access your new environment.

Moving to AEMAACS from on-premise AEM can be a complex process, but with careful planning and execution, you can ensure a smooth transition and take advantage of the benefits of a cloud-native solution.

---------------------------AMS vs AEMAACS vs Adobe Cloud Manager---------------

AMS (Adobe Managed Services), AEM as a Cloud Service (AEMAACS), and Adobe Cloud Manager are all cloud-based solutions provided by Adobe for managing Adobe Experience Cloud implementations. However, they have different focuses and serve different purposes.

AMS is a managed hosting solution provided by Adobe that offers scalable infrastructure for hosting Adobe Experience Manager and other Adobe Experience Cloud applications. With AMS, Adobe provides the infrastructure and manages the software and platform stack, including security, monitoring, and maintenance. Customers are responsible for managing their own AEM instances, including customizations and integrations.

AEMAACS, on the other hand, is a fully managed and scalable solution for creating and managing digital experiences. AEMAACS provides a cloud-native version of Adobe Experience Manager that is fully managed by Adobe, including infrastructure, software, and platform stack. AEMAACS offers several benefits over the on-premises version of AEM, including improved scalability, high availability, and faster time-to-market for new features and updates.

Adobe Cloud Manager is a cloud-based solution that helps organizations to manage the deployment, testing, and release of Adobe Experience Cloud applications. Adobe Cloud Manager provides a unified interface for managing the release management process of Adobe Experience Cloud applications, including Adobe Experience Manager, Adobe Target, Adobe Analytics, and Adobe Campaign. Adobe Cloud Manager provides a fully automated continuous integration and delivery (CI/CD) pipeline, allowing organizations to quickly and easily deploy new features and updates to their Adobe Experience Cloud applications.

In summary, AMS provides scalable infrastructure for hosting Adobe Experience Manager and other Adobe Experience Cloud applications, while AEMAACS provides a fully managed and scalable solution for creating and managing digital experiences. Adobe Cloud Manager helps organizations to manage the deployment, testing, and release of Adobe Experience Cloud applications, including Adobe Experience Manager. Customers can choose the solution that best meets their needs based on factors such as level of control, scalability, and management requirements.


------------------------------List of changes needed to migrate from AEM on-premise to AEMAACS------------

Migrating from an on-premise installation of Adobe Experience Manager (AEM) to AEM as a Cloud Service (AEMAACS) requires several changes to be made in your AEM implementation. Here are some of the key changes that you may need to make to migrate from on-premise AEM to AEMAACS:

Storage mechanism: AEM on-premise uses TarMK or MongoMK as the storage mechanism, whereas AEMAACS uses Apache Jackrabbit Oak with the S3 Data Store. You will need to migrate your data from the on-premise storage mechanism to the AEMAACS storage mechanism.

Configuration management: In AEMAACS, the configuration management is handled by Cloud Manager. You will need to migrate your configuration files to Cloud Manager and manage your configurations through Cloud Manager going forward.

Security: AEMAACS provides built-in security features, including role-based access control (RBAC) and multi-factor authentication (MFA). You will need to configure the security settings in AEMAACS and ensure that they meet your organization's needs.

Customizations and integrations: If you have any customizations or integrations in your on-premise AEM implementation, you will need to assess their compatibility with AEMAACS and make any necessary changes. AEMAACS provides a set of APIs and webhooks that can be used to integrate with external systems.

Deployment architecture: AEMAACS provides a cloud-native deployment architecture, which differs from the on-premise deployment architecture. You will need to re-architect your AEM deployment to take advantage of the AEMAACS architecture.

Content and asset migration: You will need to migrate your content and assets from your on-premise AEM installation to AEMAACS. This may involve exporting content from your on-premise AEM installation and importing it into AEMAACS.

Monitoring and maintenance: AEMAACS provides built-in monitoring and maintenance features, including automatic updates and health checks. You will need to configure these features to ensure that your AEMAACS environment is performing optimally.

Training: You will need to train your users on how to use the new AEMAACS environment. This may involve creating training materials and conducting training sessions to ensure that your users are comfortable with the new system.

In summary, migrating from on-premise AEM to AEMAACS requires several changes to be made in your AEM implementation, including changes to the storage mechanism, configuration management, security settings, customizations and integrations, deployment architecture, content and asset migration, monitoring and maintenance, and training. By making these changes, you can take advantage of the benefits of AEMAACS and ensure a smooth transition to the cloud.



----------------------Managing OSGI config in AEMAACS----

Managing OSGi (Open Services Gateway initiative) configurations in AEM as a Cloud Service (AEMAACS) is an important aspect of managing the deployment of your AEM applications. OSGi configurations in AEM define how components within the application interact with one another and how the application interacts with external services. Here are some key points to consider when managing OSGi configurations in AEMAACS:

Use the Configuration Editor in Cloud Manager: The Configuration Editor in Cloud Manager allows you to create, edit, and delete OSGi configurations for your AEM applications. It provides a user-friendly interface that allows you to configure all the properties of the OSGi configurations in a convenient way.

Versioning and Rollback: One of the main benefits of using OSGi configurations in AEMAACS is that it allows you to version and rollback your configuration changes. This ensures that you can easily revert to a previous configuration if something goes wrong. Cloud Manager provides a version history for all your configuration changes, allowing you to easily track changes and roll back as needed.

Use Cloud Manager's Approval Workflow: Cloud Manager provides an approval workflow for managing OSGi configuration changes. This workflow allows you to get approval from different stakeholders before deploying configuration changes to production. This ensures that you have the appropriate checks and balances in place before deploying any changes to your production environment.

Externalizing Configurations: AEMAACS allows you to externalize your OSGi configurations using the Cloud Manager Configurations service. This service provides a centralized repository for storing and managing your configurations. Externalizing your configurations allows you to separate configuration logic from application logic, making it easier to manage and update your configurations.

Use Environment Variables: AEMAACS supports the use of environment variables in OSGi configurations. This allows you to easily configure your applications for different environments without needing to make changes to the configuration files themselves. Instead, you can use environment variables to specify the values for the configurations, making it easier to manage your configurations across different environments.

Create a Configuration Plan: A configuration plan is a group of OSGi configurations that are applied together. AEMAACS allows you to create a configuration plan that includes all the configurations needed for a specific use case. This allows you to manage your configurations in a more structured and organized way, making it easier to manage and deploy your applications.

In summary, managing OSGi configurations in AEMAACS requires you to use the Configuration Editor in Cloud Manager, version and rollback your configuration changes, use the approval workflow, externalize your configurations, use environment variables, and create a configuration plan. By following these best practices, you can ensure that your OSGi configurations are managed in a consistent and efficient way, making it easier to deploy and manage you



if (Objects.nonNull(page)) {
final ValueMap properties = page.getProperties();
showContactInformation = properties.get(HIDE_CONTACT_INFORMATION, false);
}
return showContactInformation;

AND

Optional.ofNullable(page).map(Page::getProperties).map(p -> p.get(HIDE_CONTACT_INFORMATION)).orElse(false);



git checkout release/2023.4.1.2-RC -- brand-sdc
Reverts all files in the current branch which are changed in brand-sdc project.
-------------------------------------------------------------------------------------------------------------------
package com.acc.aem64.core.models;
import java.util.ArrayList;
import java.util.List;

import com.adobe.cq.sightly.WCMUsePojo;
import com.adobe.granite.ui.clientlibs.ClientLibrary;
import com.adobe.granite.ui.clientlibs.HtmlLibraryManager;
import com.adobe.granite.ui.clientlibs.LibraryType;

/*
* Sightly Code
*
<!-- CSS -->
<sly data-sly-use.jsObj="com.aem.community.core.components.ClientLibsModel" data-sly-list="${jsObj.cssFiles}">
    <link rel="stylesheet" href="${item}" type="text/css" async>
</sly>
<!-- JS -->
<sly data-sly-use.jsObj="com.aem.community.core.components.ClientLibsModel" data-sly-list="${jsObj.jsFiles}">
 	<script async type="text/javascript" src="${item}"></script>
</sly>
 * 
 */


public class ClientLibsModel extends WCMUsePojo {
public List<String> jsFiles = new ArrayList<String>();
public List<String> cssFiles = new ArrayList<String>();;

	@Override
	public void activate() throws Exception {

		HtmlLibraryManager clientlibmanager = getSlingScriptHelper().getService(HtmlLibraryManager.class);
		if (clientlibmanager != null) {
			String[] categoryArray = { "AEM64App.base" };
			java.util.Collection<ClientLibrary> libs = clientlibmanager.getLibraries(categoryArray, LibraryType.JS,
					false, false);
			for (ClientLibrary lib : libs) {
				String libPath = getClientLibPath(lib,LibraryType.JS);
				jsFiles.add(libPath);
			}
			
			libs = clientlibmanager.getLibraries(categoryArray, LibraryType.CSS,
					false, false);
			for (ClientLibrary lib : libs) {
				String libPath = getClientLibPath(lib,LibraryType.CSS);
				cssFiles.add(libPath);
			}
		}
	}

	private String getClientLibPath(ClientLibrary lib, LibraryType type) {
		String libPath = lib.getIncludePath(type);
		if(lib.allowProxy()) {
			libPath = libPath.replaceFirst("^/apps/", "/etc.clientlibs/");
		}
		return libPath;
	}
	
	public List<String> getJsFiles() {
		return jsFiles;
	}
	
	public List<String> getCSSFiles() {
		return cssFiles;
	}

}

Powerreview - Page Load Buy your door?
2 GoogleRecaptcha
https://qa2-atotaldoor.amarr.com/content/dam/amarr/com/us/en/images/ugd/measure_gd_2.jpg loaded onclick/later
azure script- takes 250ms


microsite homepage-
azure script- takes 900ms
/us/en-AEM Response > 700ms

https://qa2-atotaldoor.amarr.com/content/dam/amarr/com/us/en/images/home/design-your-door/design-your-door.jpg

https://qa2-atotaldoor.amarr.com/content/dam/amarr/com/us/en/carousel/Carousel5-Olympus.jpg

This block defines the I18nValue annotation. Here are the details:

@Target({ ElementType.FIELD }) specifies that the annotation can only be applied to fields.
@Retention(RetentionPolicy.RUNTIME) indicates that the annotation should be retained at runtime, allowing it to be accessed via reflection.
@InjectAnnotation is a marker annotation that indicates this is an injectable annotation.
@Source(I18nTranslationValueInjector.INJECTOR_NAME) specifies the source injector for this annotation, which is defined as I18nTranslationValueInjector.
The I18nValue annotation has two elements:

key(): It represents the key used for internationalization (i18n) translation.
injectionStrategy(): It determines the injection strategy for the annotated field.
The default value is InjectionStrategy.REQUIRED, indicating that the field must be injected.
Overall, this code defines a custom annotation I18nValue that can be used to mark fields in a class where internationalization translation is required.
The annotation provides a key for translation and allows specifying the injection strategy for the annotated field.


---------------------------------STORYBLOK-------------------------------------------------
npx @storyblok/create-demo@latest --key VxM0LBplfINHCAYtGnhwOwtt


---------------------------------AEM & OpenAI----------------------------
AI pair programms tools- For enhancing productivity
1.Github copilot
2. Amazon Code Whisper

How AI can be used with AEM:

1. Create a Content Supply Chain based on exisitng dataset provided by OpenAI.
2. Create variation of content(eg: detailed,short,warm etc) based on OpenAI.
3. Make use of Content Fragmemts to embed the content.

Demo:
reate a Content Fragment/Component which takes two inputs:
1. Content to be searched.
2. Type of content.
Populate the data based on th input from OpenAI.



AI can be integrated with (AEM) to enhance various aspects of content creation and management.
Below are few ways:
1.Building a Content Supply Chain: By using dataset provided by OpenAI, we create content supply chain within AEM.
2.Generating Content Variations: With OpenAI, AEM can generate content in various styles(e.g., detailed, warm, short),
ensuring content is tailored to specific contexts and user preferences.
3.Leveraging Content Fragments: AEM's Content Fragments feature can be utilized to embed AI-generated content seamlessly.


timusyadav
Bluewhale01!

{openAiCfModelVersion2List{
items {
prompt
}
}}

{openAiCfModelVersion2ByPath(_path:"/content/dam/ai-generated/coorg"){
item{
prompt
}


nvm list - gives installed versions.
nvm install 16.16.0 - to install new version
nvm use 16.16.0  - to set this version

------------------------------------------------------------------------------
1. Create GraphQL endpoint
   tools > general > GraphQL >Create

2. Query Content using GraphQL:
   -> Open GraphiQl -> http://localhost:4502/aem/graphiql.html


He has good hands on experience on AEM HTL, workflow, dispatcher, OSGi, XF, DAM, Sling jobs (only async sling jobs), AEM Query, Sling API, Node API, Annotations, User Management (basics)
He has good conecpts of Java used in AEM, but not explicitly on Java.
He has basic hands on experience on  Ecma script, graphQL.
He was not able to answers questions about advanced user management, aem guides and other 3rd party integrations, repo initiazer.

SAML
LDAP is the protocol or communication process that will enable users to access a network resource through a directory service.
SSO is a convenient authentication method that allows users to access multiple applications and systems using just one login.
OKTA

SSO vs LDAP
Developers could use LDAP to allow SSO if a single login were to grant the user access to all databases, apps, and devices on that server.

SSO vs SAML
SAML refers to the process of granting and authenticating user access specifically to cloud and web applications. Developers commonly use SAML protocols to provide access to multiple applications
or systems at once with just one login through a directory service—which would be the equivalent of SSO verification.

Cognito
Emarsys
Navision

mvn -B archetype:generate -D archetypeGroupId=com.adobe.aem -D archetypeArtifactId=aem-project-archetype -D archetypeVersion=43 -D appTitle="ACE" -D appId="ace" -D groupId="com.ace" -D aemVersion=cloud

ssh-keygen -t ed25519 -C "sumit.yadav@nagarro.com"

ssh-add C:\Users\sumityadav/.ssh/id_ed25519

Step1 .

username: sumit-yadav-nagarro-com
password: app5deuhxr2cthtbmbmzlqujibxibhruknlfpbqgho3mf3i3rmsa

git remote add ace-adobe https://git.cloudmanager.adobe.com/aceautoclub/ACEAUTOCLUBEUROPA/
git fetch --all
git checkout develop
git pull
git fetch --all
git push ace-adobe develop




git push ace-adobe feature/WEB-309-ace-dispatcher-config

Incoming Request ---> vhost mapping ---> farm resolution --->

What file loads first in dispathcer?
1.  conf.d/dispatcher_vhost.conf
2.  Above file will include these in order: dispatcher.any and enabled_vhost.conf.

dispatcher.any -> file will have all the farms define and helps us to configure clientheaders,
virtualhosts, renders, filter, vanity_urls, propagateSyndPost, cache.

enabled_vhost.conf -> file will help us to declare port, hostname, log file position, whitelist rules and define rewrite rules.

What will be the journey from end users perspective for mysite.com/home.html
1. Request reaches to Dispatcher via CDN.
2. Dispatcher will check all vhost entries  under /conf.d/available_vhosts and matches the value in ServerName/ServerAlias.
3. In this case, a vhost entry should be present as mysite.com in mysite.com.vhost file.
4. It will then load the farm file correspondig to this vhost under conf.dispatcher.d/enabled_farms.
5. In this case, a farm file mysite.farm should be present.
6. It will load mysite.com/home.html after check all the config in the farm file.


1. Install Apache Web Server and verify http://localhost:8080
2. Included Dispatcher Module.
3. httpd.conf --> dispatcher.any (config for project)
   --> vhost file

----------------------------------------------AUTHENTICATION--------------------------------------------
What is IDP?

IDP(Identity Provider) -> is a system/service that manages and stores identity information used to authenticate and authorize individuals.
IDPs commonly use protocols like: SAML, OpenID Connect, OAuth , LDAP etc.
IDPs often facilitate Single Sign-On(SSO)functionality.
Few IDP's in market - Okta,Microsoft AD,AWS Cognito,Auth0,OneLogin,Google Identity Platform,Ping Identity etc.

How are IDP,IAM,IMS related or different?

IDP -> serves as the single point of reference for user identity authentication.IDPs focus on user authentication and assertion.
They validate user credentials and issue tokens that grant access to resources, often used in Single Sign-On (SSO) scenarios.

IAM -> 'Identity and Access Management',IAM solutions handle the entire lifecycle of digital identities, including provisioning,
authentication, authorization, access control, identity governance, and compliance.
Azure Active Directory (Azure AD), Okta Identity Cloud, AWS IAM are few examples.

IMS -> 'Identity Management System', represents the broader suite of tools or systems used for managing identities, access, and related
policies within an organization,which often encompasses IDP and IAM functionalities.

Note: While there isn't a widely recognized standalone category known as IMS, various identity management solutions or systems collectively contribute
to IMS functionalities, often inclusive of IDP and IAM capabilities.

What is LDAP ?

LDAP(Lightweight Directory Access Protocol) -> primarily a protocol i.e set of rules and standards that govern how information is accessed
and managed within a directory server. It's often used to interact with directory servers.
Directory Server/LDAP Server/LDAP IDP -> This is the actual software or system that implements the LDAP protocol. The server stores and manages the directory information,
such as user details, passwords, organizational structures, and more, following the LDAP protocol.

Note: Think of LDAP as the language (protocol) used to talk to and interact with the directory server (the system where information is stored).
Commonly used Directory Servers are : Microsoft AD, openLDAP, ApacheDS, Jumpcloud.

AEM and LDAP Server Integration. Why and How?
Think LDAP Server as a phonebook for computer systems. It's a way to store and organize information about users, like their names, passwords,
and other details, in a structured manner.
When AEM integrates with LDAP Server, it means AEM can use the information stored in the LDAP Server phonebook to manage users' access to its
digital experiences. Instead of creating separate user accounts and passwords in AEM, it can use the user information stored in LDAP Server.
In essence: AEM LDAP integration lets AEM use LDAP as a central place to manage user information and access, making it more efficient, secure,
and easier to manage for organizations.

Will the user in LDAP exist in AEM?
YES, User accounts are synchronized between the LDAP server and the AEM repository.

What is the authentication flow?
When a user tries to log in to AEM, AEM validates the user's credentials against the information stored in LDAP to verify if they're correct.  
To improve performance, successfully validated credentials can be cached by the repository, with an expiry timeout to ensure
that revalidation does occur after an appropriate period.
When an account is removed from the LDAP server, validation is no longer granted and access to the repository is denied. Details of LDAP accounts
that are saved in the repository can also be purged.

Official Doc: https://experienceleague.adobe.com/docs/experience-manager-65/content/security/ldap-config.html?lang=en
AEM & JumpCloud Integration: https://medium.com/techtouch/integrate-aem-6-5-with-ldap-using-jumpcloud-ldap-as-a-service-d887fec9fe6f


What is SSO ?
Single Sign On (SSO) allows a user to access multiple systems after providing authentication credentials once.
A separate system (known as the trusted authenticator) performs the authentication and provides AEM with the user credentials.
AEM checks and enforces the access permissions for the user (that is, determines which resources the user is allowed to access).

How SSO works in AEM ?
The SSO Authentication Handler service ( com.adobe.granite.auth.sso.impl.SsoAuthenticationHandler) processes the authentication
results that the trusted authenticator provides.
The SSO Authentication Handler searches for an SSO Identifier (SSID) as the value of a special attribute in the following locations in this order:
1. Request Headers
2. Cookies
3. Request Parameters
   When a value is found, the search is finished and this value is used.

How is SSO attribute configured ?
As we know that, SSO Authentication Handler searches for an SSID as the value of a special attribute.
We need to configure two services to recognize the name of the attribute that stores the SSID:
1. The login module
2. The SSO Authentication service (Adobe Granite SSO Authentication Handler)
   Specify the same attribute name for both services.

Official ref: https://experienceleague.adobe.com/docs/experience-manager-65/content/security/single-sign-on.html?lang=en

What is SAML 2.0 Authentication Handler ?
AEM ships with a SAML 2.0 Authentication handler.
It supports:
1. automatic creation of users.
2. signing and encryption of messages.
3. synching groups to existing ones in AEM.
4. service Provider and Identity Provider initiated authentication.
   This handler stores the encrypted SAML response message in the user-node ( usernode/samlResponse) to facilitate communication
   with a third-party Service Provider.

Ref: https://experienceleague.adobe.com/docs/experience-manager-65/content/security/saml-2-0-authenticationhandler.html?lang=en

Moving to Cloud
adobe aio commands

50/30




pom dependency scope
CUG
granite:rendercondition
maven bundle plugin
AEM SPA Cors error
@Attribute definition mask
Sling Model Exporter property expose scenario
oak Index, create property index

Create Dispatcher symlink in windows.
Run below command from bash in enabled folder.

export MSYS=winsymlinks:nativestrict

ln -sfv ../available_farms/001_ace.farm 001_ace.farm
ln -sfv ../available_vhosts/001_ace.de.vhost 001_ace.de.vhost


db66e6cbd3f0d3ada817dad202788618f8fa7af8152cef323fda1529286ce470
O
curl -X PURGE https://publish-20353-1224652.adobeaemcloud.com/content/ace/de/de/home.html -H "x-aem-purge-key:db66e6cbd3f0d3ada817dad202788618f8fa7af8152cef323fda1529286ce470"

Container Properties
./layout - defines the layout type, either simple (default) or responsiveGrid; if no value is defined, the component will fallback to the value defined by the component's policy

AEM on premise bring your own CDN (eg. Akamai)
AEM As a cloud service - adobe own Fastly

Author -> via replication agents publish page -> Publish -> Dispathcer(Apache Webserver- security,load balancing,url rewrites,caching)-> CDN -> end user.

From end user's perspective.
1. ace.de/reisen.html
2. If page is cached in CDN -> return the content.
3. If not cached, request goes to dispathcer.
4. If dispathcer has a cache -> returns to CDN -> create a new Cache.
5. If dispathcer doesn't has a cache -> request goes to publish -> publish returns the page to dispatcher -> dispatcher will cache and it will return to CDN and CDN will also cache.

Development-
1. project structure using maven archetype.(command can be googled)
2. generates different folders like ui.apps,core etc...
3. we'll deploy this project to local aem running instance.



AEM Component -> custom text field, url field -> confugured value should be rendered.
AEM Page -> is based on some predined editable template.

npx aemfed -t \"http://admin:admin@localhost:4502\" -w \"C:/Users/sumityadav/OneDrive - Nagarro/Documents/ace/web-aem-nagarro/ui.apps/src/main/content/jcr_root/\"
npx aemfed -t "http://admin:admin@localhost:4502" -w "ui.apps/src/main/content/jcr_root/"

?c=100
if c param ->
link -> get canvasser num from mapping -> call the number

1. use content fragment < 150

Affiliate Hash
Map <String,Partner>

http://localhost:4502/content/ace.sitemap-index.xml
ace.de/sitemap-index.xml

http://localhost:4502/content/ace.sitemap.de-de-sitemap.xml
ace.de/sitemap.xml

Rewrite rule


[11:38 AM] Mythry Jhansi
mvn -B org.apache.maven.plugins:maven-archetype-plugin:3.2.1:generate -D archetypeGroupId=com.adobe.aem -D archetypeArtifactId=aem-project-archetype -D archetypeVersion=39 -D appTitle="AEM Learning Project" -D appId="jhansi" -D groupId="com.adobe.aem.jhansi" -D artifactId="aem-jhansi" -D package="com.adobe.aem.jhansi" -D version="0.0.1-SNAPSHOT" -D aemVersion="cloud"