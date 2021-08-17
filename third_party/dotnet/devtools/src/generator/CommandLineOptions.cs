using CommandLine;
using System;
using System.Collections.Generic;
using System.Text;

namespace OpenQA.Selenium.DevToolsGenerator
{
    public class CommandLineOptions
    {
        public CommandLineOptions()
        {
        }

        [Option(
            'f',
            "force-download",
            Default = false,
            HelpText = "Forces the Chrome Protocol Definition to be downloaded from source even if it already exists.")]
        public bool ForceDownload { get; set; }

        [Option(
            'q',
            "quiet",
            Default = false,
            HelpText = "Suppresses console output.")]
        public bool Quiet { get; set; }

        [Option(
            "force",
            Default = true,
            HelpText = "Forces the output directory to be overwritten")]
        public bool ForceOverwrite { get; set; }

        [Option(
            'o',
            "output-path",
            Default = "/Users/Puja/Documents/GitHub/selenium/bazel-bin/dotnet/src/webdriver/cdp/OutputProtocol/",
            HelpText ="Indicates the folder that will contain the generated class library [Default: ./OutputProtocol]")]
        public string OutputPath { get; set; }

        [Option(
            'b',
            "browser-protocol-path",
            Default = "/Users/Puja/Documents/GitHub/selenium/common/devtools/browser_protocol.json",
            HelpText = "Indicates the path to the Chromium Debugging Browser Protocol JSON file to use. [Default: browser_protocol.json]")]
        public string BrowserProtocolPath { get; set; }

        [Option(
            'j',
            "js-protocol-path",
            Default = "/Users/Puja/Documents/GitHub/selenium/common/devtools/js_protocol.json",
            HelpText = "Indicates the path to the Chromium Debugging JavaScript Protocol JSON file to use. [Default: js_protocol.json]")]
        public string JavaScriptProtocolPath { get; set; }

        [Option(
            't',
            "templates-path",
            Default = "/Users/Puja/Documents/GitHub/selenium/third_party/dotnet/devtools/src/generator/Templates/",
            HelpText = "Indicates the path to the code generation templates file.")]
        public string TemplatesPath { get; set; }

        [Option(
            's',
            "settings",
            Default = "/Users/Puja/Documents/GitHub/selenium/third_party/dotnet/devtools/src/generator/Templates/settings.json",
            HelpText = "Indicates the path to the code generation settings file. [Default: ./Templates/settings.json]")]
        public string Settings { get; set; }
    }
}
