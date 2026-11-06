CREATE DATABASE IF NOT EXISTS worldparliamentsimulator;

CREATE TABLE worldparliamentsimulator.countrys (
    `country_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `continent` VARCHAR(255),
    `flag_emoji` VARCHAR(255),
    `capital` VARCHAR(255),
    `gdp` DOUBLE,
    `population` INT,
    `army_size` INT,
    `resources` VARCHAR(255),
PRIMARY KEY (country_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.leaders (
    `leader_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `country_id` INT,
    `ideology` VARCHAR(255),
    `history` VARCHAR(255),
PRIMARY KEY (leader_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.ideologys (
    `ideology_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `tax_rate` DOUBLE,
    `public_reaction` VARCHAR(255),
    `army_support` VARCHAR(255),
PRIMARY KEY (ideology_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.peoples (
    `people_id` INT AUTO_INCREMENT,
    `country_id` INT,
    `happiness` DOUBLE,
PRIMARY KEY (people_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.economys (
    `economy_id` INT AUTO_INCREMENT,
    `country_id` INT,
    `investment_in_education` DOUBLE,
    `investment_in_health` DOUBLE,
    `investment_in_factories` DOUBLE,
    `military_spending` DOUBLE,
PRIMARY KEY (economy_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.militarys (
    `military_id` INT AUTO_INCREMENT,
    `country_id` INT,
    `soldiers` INT,
    `tanks` INT,
    `aircraft` INT,
PRIMARY KEY (military_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.infrastructures (
    `infrastructure_id` INT AUTO_INCREMENT,
    `country_id` INT,
    `schools` INT,
    `hospitals` INT,
    `roads` INT,
    `clean_energy_sources` INT,
PRIMARY KEY (infrastructure_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.continent_problems (
    `continent_problem_id` INT AUTO_INCREMENT,
    `continent` VARCHAR(255),
    `issues` VARCHAR(255),
PRIMARY KEY (continent_problem_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.alliances (
    `alliance_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `member_countries` VARCHAR(255),
PRIMARY KEY (alliance_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.nuclear_policys (
    `nuclear_policy_id` INT AUTO_INCREMENT,
    `country_id` INT,
    `has_nukes` BOOLEAN,
    `is_protected` BOOLEAN,
PRIMARY KEY (nuclear_policy_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.diplomacys (
    `diplomacy_id` INT AUTO_INCREMENT,
    `country_id` INT,
    `alliances_formed` INT,
    `wars_declared` INT,
    `peace_treaties_signed` INT,
    `economic_embargos` INT,
PRIMARY KEY (diplomacy_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.espionages (
    `espionage_id` INT AUTO_INCREMENT,
    `country_id` INT,
    `operations_conducted` INT,
PRIMARY KEY (espionage_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.organizations (
    `organization_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `policies` VARCHAR(255),
PRIMARY KEY (organization_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.events (
    `event_id` INT AUTO_INCREMENT,
    `country_id` INT,
    `type` VARCHAR(255),
    `impact` DOUBLE,
    `date` DATETIME,
PRIMARY KEY (event_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.communications (
    `communication_id` INT AUTO_INCREMENT,
    `sender_country_id` INT,
    `receiver_country_id` INT,
    `message` VARCHAR(255),
    `reaction_emoji` VARCHAR(255),
PRIMARY KEY (communication_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE worldparliamentsimulator.turns (
    `turn_id` INT AUTO_INCREMENT,
    `action_chosen` VARCHAR(255),
    `country_id` INT,
    `world_reaction` VARCHAR(255),
    `random_event` VARCHAR(255),
    `month` INT,
PRIMARY KEY (turn_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
