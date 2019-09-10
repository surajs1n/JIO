# This line has to be updated on every place, possible.
scriptPath <- "/Users/suraj.s/Workspace/JIO/src/main/resources/results";

# Read in all csv files.
StringGeneratorMetrics         <- read.csv(file = file.path(scriptPath, "StringGenerator-Metrics.csv") , header = TRUE, sep = ",");
FileOutputWithoutBufferMetrics <- read.csv(file = file.path(scriptPath, "FileOutputWithoutBuffer-Metrics.csv"), header = TRUE, sep = ",");
FileOutputWithBufferMetrics    <- read.csv(file = file.path(scriptPath, "FileOutputWithBuffer-Metrics.csv"), header = TRUE, sep = ",");
FileInputWithoutBufferMetrics  <- read.csv(file = file.path(scriptPath, "FileInputWithoutBuffer-Metrics.csv"), header = TRUE, sep = ",");
FileInputWithBufferMetrics     <- read.csv(file = file.path(scriptPath, "FileInputWithBuffer-Metrics.csv"), header = TRUE, sep = ",");

FileOutputMetircs <- merge(FileOutputWithoutBufferMetrics, FileOutputWithBufferMetrics, by=c('File.Length'), all=T);
#colnames(FileOutputMetircs) <- c("File Length", "Non-Buffered OutputStream Time", "Buffered OutputStream Time");
show(FileOutputMetircs);

FileInputMetrics <- merge(FileInputWithoutBufferMetrics, FileInputWithBufferMetrics, by=c('File.Length'), all=T);
#colnames(FileInputMetrics)  <- c("File Length", "Non-Buffered InputStream Time", "Buffered InputStream Timer ");
show(FileInputMetrics);

# Install extra packages :- ggplot2, reshape2.
list.of.packages <- c("ggplot2","reshape2");
new.packages <- list.of.packages[!(list.of.packages %in% installed.packages()[,"Package"])];
if(length(new.packages)) {
	install.packages(new.packages)
}
require(ggplot2);
require(reshape2);

# Create a graph of FileOutputStream.
df <- melt(FileOutputMetircs, id.vars = 'File.Length', variable.name = 'Time');
ggplot(df, aes(File.Length, value)) + geom_line(aes(colour = Time));

# Create a graph of FileInputStream.
df <- melt(FileInputMetrics, id.vars = 'File.Length', variable.name = 'Time');
ggplot(df, aes(File.Length, value)) + geom_line(aes(colour = Time));