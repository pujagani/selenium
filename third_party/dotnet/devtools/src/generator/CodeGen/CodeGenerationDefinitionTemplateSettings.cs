namespace OpenQA.Selenium.DevToolsGenerator.CodeGen
{
    using Newtonsoft.Json;

    /// <summary>
    /// Represents settings around Definition templates.
    /// </summary>
    public class CodeGenerationDefinitionTemplateSettings
    {
        public CodeGenerationDefinitionTemplateSettings()
        {
            //Set Defaults;
            DomainTemplate = new CodeGenerationTemplateSettings
            {
                TemplatePath = "/Users/Puja/Documents/GitHub/selenium/third_party/dotnet/devtools/src/generator/Templates/domain.hbs",
                OutputPath = "{{domainName}}//{{className}}Adapter.cs",
            };

            CommandTemplate = new CodeGenerationTemplateSettings {
                TemplatePath = "/Users/Puja/Documents/GitHub/selenium/third_party/dotnet/devtools/src/generator/Templates/command.hbs",
                OutputPath = "{{domainName}}//{{className}}Command.cs",
            };

            EventTemplate = new CodeGenerationTemplateSettings
            {
                TemplatePath = "/Users/Puja/Documents/GitHub/selenium/third_party/dotnet/devtools/src/generator/Templates/event.hbs",
                OutputPath = "{{domainName}}//{{className}}EventArgs.cs",
            };

            TypeObjectTemplate = new CodeGenerationTemplateSettings
            {
                TemplatePath = "/Users/Puja/Documents/GitHub/selenium/third_party/dotnet/devtools/src/generator/Templates/type-object.hbs",
                OutputPath = "{{domainName}}//{{className}}.cs",
            };

            TypeHashTemplate = new CodeGenerationTemplateSettings
            {
                TemplatePath = "/Users/Puja/Documents/GitHub/selenium/third_party/dotnet/devtools/src/generator/Templates/type-hash.hbs",
                OutputPath = "{{domainName}}//{{className}}.cs",
            };

            TypeEnumTemplate = new CodeGenerationTemplateSettings
            {
                TemplatePath = "/Users/Puja/Documents/GitHub/selenium/third_party/dotnet/devtools/src/generator/Templates/type-enum.hbs",
                OutputPath = "{{domainName}}//{{className}}.cs",
            };
        }

        [JsonProperty("domainTemplate")]
        public CodeGenerationTemplateSettings DomainTemplate
        {
            get;
            set;
        }

        [JsonProperty("commandTemplate")]
        public CodeGenerationTemplateSettings CommandTemplate
        {
            get;
            set;
        }

        [JsonProperty("eventTemplate")]
        public CodeGenerationTemplateSettings EventTemplate
        {
            get;
            set;
        }

        [JsonProperty("typeObjectTemplate")]
        public CodeGenerationTemplateSettings TypeObjectTemplate
        {
            get;
            set;
        }

        [JsonProperty("typeHashTemplate")]
        public CodeGenerationTemplateSettings TypeHashTemplate
        {
            get;
            set;
        }

        [JsonProperty("typeEnumTemplate")]
        public CodeGenerationTemplateSettings TypeEnumTemplate
        {
            get;
            set;
        }

    }
}
