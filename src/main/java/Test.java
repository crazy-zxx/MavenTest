public class Test {

    /**
     * Maven就是是专门为Java项目打造的管理和构建工具，它的主要功能有：
     * 提供了一套标准化的项目结构；
     * 提供了一套标准化的构建流程（编译，测试，打包，发布……）；
     * 提供了一套依赖管理机制。
     *
     * 一个使用Maven管理的普通的Java项目，它的目录结构默认如下：
     *
     * a-maven-project
     * ├── pom.xml
     * ├── src
     * │   ├── main
     * │   │   ├── java
     * │   │   └── resources
     * │   └── test
     * │       ├── java
     * │       └── resources
     * └── target
     *
     * 最关键的一个项目便是描述文件pom.xml，具体见该项目的pom.xml文件
     *
     * Maven不但有标准化的项目结构，而且还有一套标准化的构建流程，可以自动化实现编译，打包，发布，等等。
     *
     * Maven的生命周期由一系列阶段（phase）构成，以内置的生命周期default为例，它包含以下phase：
     * validate
     * initialize
     * generate-sources
     * process-sources
     * generate-resources
     * process-resources
     * compile
     * process-classes
     * generate-test-sources
     * process-test-sources
     * generate-test-resources
     * process-test-resources
     * test-compile
     * process-test-classes
     * test
     * prepare-package
     * package
     * pre-integration-test
     * integration-test
     * post-integration-test
     * verify
     * install
     * deploy
     *
     * Maven另一个常用的生命周期是clean，它会执行3个phase：
     * pre-clean
     * clean （注意这个clean不是lifecycle而是phase）
     * post-clean
     *
     * 在实际开发过程中，经常使用的命令有：
     * mvn clean：清理所有生成的class和jar；
     * mvn clean compile：先清理，再执行到compile；
     * mvn clean test：先清理，再执行到test，因为执行test前必须执行compile，所以这里不必指定compile；
     * mvn clean package：先清理，再执行到package。
     *
     * 大多数phase在执行过程中，因为我们通常没有在pom.xml中配置相关的设置，所以这些phase什么事情都不做。
     *
     * 经常用到的phase其实只有几个：
     * clean：清理
     * compile：编译
     * test：运行测试
     * package：打包
     *
     * lifecycle相当于Java的package，它包含一个或多个phase；
     * phase相当于Java的class，它包含一个或多个goal；
     * goal相当于class的method，它其实才是真正干活的。
     *
     * 大多数情况，我们只要指定phase，就默认执行这些phase默认绑定的goal，只有少数情况，我们可以直接指定运行一个goal，例如，启动Tomcat服务器：
     * mvn tomcat:run
     *
     * 实际上，执行每个phase，都是通过某个插件（plugin）来执行的，Maven本身其实并不知道如何执行compile，
     * 它只是负责找到对应的compiler插件，然后执行默认的compiler:compile这个goal来完成编译。
     * 所以，使用Maven，实际上就是配置好需要使用的插件，然后通过phase调用它们
     *
     * Maven已经内置了一些常用的标准插件：
     * 插件名称	对应执行的phase
     * clean	clean
     * compiler	compile
     * surefire	test
     * jar	    package
     * 如果标准插件无法满足需求，我们还可以使用自定义插件。使用自定义插件的时候，需要声明。
     * 例如，使用maven-shade-plugin可以创建一个可执行的jar，要使用这个插件，需要在pom.xml中声明它：
     *
     * 在软件开发中，把一个大项目分拆为多个模块是降低软件复杂度的有效方法
     * Maven支持模块化管理，可以把一个大项目拆成几个模块：
     *      可以通过继承在parent的pom.xml统一定义重复配置；
     *      可以通过<modules>编译多个模块。
     * Maven可以有效地管理多个模块，我们只需要把每个模块当作一个独立的Maven项目，它们有各自独立的pom.xml
     * 如果模块A和模块B的pom.xml高度相似，那么，我们可以提取出共同部分作为parent
     * 注意到parent的<packaging>是pom而不是jar，因为parent本身不含任何Java代码。编写parent的pom.xml只是为了在各个模块中减少重复的配置。
     * 模块A、模块B都可以直接从parent继承，大幅简化了pom.xml的编写。
     * 如果模块A依赖模块B，则模块A需要模块B的jar包才能正常编译，我们需要在模块A中引入模块B
     * 最后，在编译的时候，需要在根目录创建一个pom.xml统一编译
     *
     * mvnw是Maven Wrapper的缩写。因为我们安装Maven时，默认情况下，系统所有项目都会使用全局安装的这个Maven版本。但是，对于某些项目来说，它可能必须使用某个特定的Maven版本，
     * 这个时候，就可以使用Maven Wrapper，它可以负责给这个特定的项目安装指定版本的Maven，而其他项目不受影响。
     * 简单地说，Maven Wrapper就是给一个项目提供一个独立的，指定版本的Maven给它使用。
     * 安装Maven Wrapper最简单的方式是在项目的根目录（即pom.xml所在的目录）下运行安装命令：
     *      mvn -N io.takari:maven:0.7.6:wrapper
     * 它会自动使用最新版本的Maven。注意0.7.6是Maven Wrapper的版本。最新的Maven Wrapper版本可以去官方网站查看。
     * 如果要指定使用的Maven版本，使用下面的安装命令指定版本，例如3.3.3：
     *      mvn -N io.takari:maven:0.7.6:wrapper -Dmaven=3.3.3
     * 发现多了mvnw、mvnw.cmd和.mvn目录，我们只需要把mvn命令改成mvnw就可以使用跟项目关联的Maven。
     *
     * Maven Wrapper的另一个作用是把项目的mvnw、mvnw.cmd和.mvn提交到版本库中，可以使所有开发人员使用统一的Maven版本。
     *
     */

    public static void main(String[] args) {

        System.out.println("hello");

    }

}
