package io.os;

import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.Firmware;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class OperatingSystemDetails {
    private final static double BILLION = 1000000000.0;
    private final static double MEGA = 1024 * 1024;
    private final Map<String, String> osDetails = new LinkedHashMap<>();

    public Map<String, String> fetchOSDetails() {
        final SystemInfo si = new SystemInfo();
        final HardwareAbstractionLayer hal = si.getHardware();
        final OperatingSystem os = si.getOperatingSystem();

        getComputerSystemDetails(hal.getComputerSystem());
        getOperatingSystemDetails(os);
        getProcessorDetails(hal.getProcessor());
        getSensorsDetails(hal.getSensors());
        getMemoryDetails(hal.getMemory());
        getHardDisksDetails(hal.getDiskStores());

        for(Map.Entry<String, String> osDetail : osDetails.entrySet()) {
            System.out.println(osDetail.getKey() + " => " + osDetail.getValue());
        }

        return osDetails;
    }

    public String getCSVHeaderOfOSDetails() {
        final Set<String> headerList = osDetails.keySet();
        Iterator<String> it = headerList.iterator();

        String result = it.next();
        while (it.hasNext()) {
            result += "," + it.next();
        }

        System.out.println(result);
        return result;
    }

    public String getCSVValuesofOSDetails() {
        final Collection<String> values = osDetails.values();
        Iterator<String> it = values.iterator();

        String result = it.next();
        while (it.hasNext()) {
            result += "," + it.next();
        }

        System.out.println(result);
        return result;
    }

    private void getOperatingSystemDetails(final OperatingSystem os) {
        osDetails.put("OS Type", String.valueOf(SystemInfo.getCurrentPlatformEnum()));
        osDetails.put("OS Manufacturer", String.valueOf(os.getManufacturer()));
        osDetails.put("OS Family", String.valueOf(os.getFamily()));
        osDetails.put("OS Bitness", String.valueOf(os.getBitness()));
        osDetails.put("OS Version", String.valueOf(os.getVersion()));
        osDetails.put("OS Name", String.valueOf(os));
        osDetails.put("OS Total Process Count", String.valueOf(os.getProcessCount()));
        osDetails.put("OS Total Thread Count", String.valueOf(os.getThreadCount()));
    }

    private void getComputerSystemDetails(final ComputerSystem computerSystem) {
        osDetails.put("Computer Manufacturer", String.valueOf(computerSystem.getManufacturer()));
        osDetails.put("Computer Model", String.valueOf(computerSystem.getModel()));
        osDetails.put("Computer Serial Number", String.valueOf(computerSystem.getSerialNumber()));
        final Firmware firmware = computerSystem.getFirmware();
        osDetails.put("Firmware Manufacturer", String.valueOf(firmware.getManufacturer()));
        osDetails.put("Firmware Name", String.valueOf(firmware.getName()));
        osDetails.put("Firmware Description", String.valueOf(firmware.getDescription()));
        osDetails.put("Firmware Version", String.valueOf(firmware.getVersion()));
        osDetails.put("Firmware Release Date", String.valueOf(firmware.getReleaseDate()));
        final Baseboard baseboard = computerSystem.getBaseboard();
        osDetails.put("BaseBoard Manufacturer", String.valueOf(baseboard.getManufacturer()));
        osDetails.put("BaseBoard Model",String.valueOf(baseboard.getModel()));
        osDetails.put("BaseBoard Serial Number",String.valueOf(baseboard.getSerialNumber()));
        osDetails.put("BaseBoard Version",String.valueOf(baseboard.getVersion()));
    }

    private void getProcessorDetails(final CentralProcessor processor) {
        osDetails.put("Processor Vendor", String.valueOf(processor.getVendor()));
        osDetails.put("Processor Name", String.valueOf(processor.getName()));
        osDetails.put("Processor Frequency (GHz)", String.valueOf(processor.getVendorFreq()/BILLION));
        osDetails.put("Processor 64-bit", String.valueOf(processor.isCpu64bit()));
        osDetails.put("Processor Over-Clock Frequency (GHz)", String.valueOf(processor.getMaxFreq()/BILLION));
        osDetails.put("Processor Identifier", String.valueOf(processor.getIdentifier()));
        osDetails.put("Processor Stepping", String.valueOf(processor.getStepping()));
        osDetails.put("Processor Model", String.valueOf(processor.getModel()));
        osDetails.put("Processor Family", String.valueOf(processor.getFamily()));
        osDetails.put("Number of Logical Processors", String.valueOf(processor.getLogicalProcessorCount()));
        osDetails.put("Number of Physical Processors", String.valueOf(processor.getPhysicalProcessorCount()));
        osDetails.put("Number of Processor Sockets", String.valueOf(processor.getPhysicalPackageCount()));
        osDetails.put("Number of Context Switches", String.valueOf(processor.getContextSwitches()));
        osDetails.put("Number of Interrupts", String.valueOf(processor.getInterrupts()));
    }

    private void getSensorsDetails(final Sensors sensors) {
        osDetails.put("CPU Temperature (*C)", String.valueOf(sensors.getCpuTemperature()));
        osDetails.put("CPU Voltage (V)", String.valueOf(sensors.getCpuVoltage()));
    }

    private void getMemoryDetails(final GlobalMemory memory) {
        osDetails.put("RAM Total Space (MB)", String.valueOf(memory.getTotal()/MEGA));
        osDetails.put("RAM Available Space (MB)", String.valueOf(memory.getAvailable()/MEGA));
        osDetails.put("RAM Page Size (Byte)", String.valueOf(memory.getPageSize()));
        osDetails.put("Virtual Memory Total Space (MB)", String.valueOf(memory.getVirtualMemory().getSwapTotal()/MEGA));
        osDetails.put("Virtual Memory Used Space (MB)", String.valueOf(memory.getVirtualMemory().getSwapUsed()/MEGA));
        osDetails.put("Virtual Memory Page-In Space (MB)", String.valueOf(memory.getVirtualMemory().getSwapPagesIn()/MEGA));
        osDetails.put("Virtual Memory Page-Out Space (MB)", String.valueOf(memory.getVirtualMemory().getSwapPagesOut()/MEGA));
    }

    private void getHardDisksDetails(final HWDiskStore[] diskStores) {
        for(int i=1; i<=diskStores.length; i++) {
            osDetails.put(String.format("Disk-" + i + " Name"), String.valueOf(diskStores[i-1].getName()));
            osDetails.put(String.format("Disk-" + i + " Model"), String.valueOf(diskStores[i-1].getModel()));
            osDetails.put(String.format("Disk-" + i + " Serial"), String.valueOf(diskStores[i-1].getSerial()));
            osDetails.put(String.format("Disk-" + i + " Size (GB)"), String.valueOf(diskStores[i-1].getSize()/(MEGA*1024)));
            osDetails.put(String.format("Disk-" + i + " Reads (MB)"), String.valueOf(diskStores[i-1].getReads()/MEGA));
            osDetails.put(String.format("Disk-" + i + " ReadBytes (MB)"), String.valueOf(diskStores[i-1].getReadBytes()/MEGA));
            osDetails.put(String.format("Disk-" + i + " Writes (MB)"), String.valueOf(diskStores[i-1].getWrites()/MEGA));
            osDetails.put(String.format("Disk-" + i + " WriteBytes (MB)"), String.valueOf(diskStores[i-1].getWriteBytes()/MEGA));
            osDetails.put(String.format("Disk-" + i + " Current Queue Length"), String.valueOf(diskStores[i-1].getCurrentQueueLength()));
            osDetails.put(String.format("Disk-" + i + " Transfer Time"), String.valueOf(diskStores[i-1].getTransferTime()));
            osDetails.put(String.format("Disk-" + i + " TimeStamp"), String.valueOf(diskStores[i-1].getTimeStamp()));

            final HWPartition [] hwPartitions = diskStores[i-1].getPartitions();
            for(int j=1; j<=hwPartitions.length; j++) {
                osDetails.put(String.format("Disk-" + i + " Partition-" + j + " Name"), String.valueOf(hwPartitions[j-1].getName()));
                osDetails.put(String.format("Disk-" + i + " Partition-" + j + " Type"), String.valueOf(hwPartitions[j-1].getType()));
                osDetails.put(String.format("Disk-" + i + " Partition-" + j + " Identification"), String.valueOf(hwPartitions[j-1].getIdentification()));
                osDetails.put(String.format("Disk-" + i + " Partition-" + j + " UUID"), String.valueOf(hwPartitions[j-1].getUuid()));
                osDetails.put(String.format("Disk-" + i + " Partition-" + j + " Size (GB)"), String.valueOf(hwPartitions[j-1].getSize()/(MEGA*1024)));
                osDetails.put(String.format("Disk-" + i + " Partition-" + j + " Major"), String.valueOf(hwPartitions[j-1].getMajor()));
                osDetails.put(String.format("Disk-" + i + " Partition-" + j + " Minor"), String.valueOf(hwPartitions[j-1].getMinor()));
                osDetails.put(String.format("Disk-" + i + " Partition-" + j + " MountPoint"), String.valueOf(hwPartitions[j-1].getMountPoint()));
            }
        }
    }
}
